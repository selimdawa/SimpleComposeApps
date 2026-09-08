package com.flatcode.simplecomposeapps.pdfreader.viewmodel

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.pdfreader.data.PdfDao
import com.flatcode.simplecomposeapps.pdfreader.data.PdfEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject
import javax.net.ssl.SSLException

data class PdfUiState(
    val uri: Uri? = null,
    val pdfData: ByteArray? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val pageCount: Int = 0,
    val currentPage: Int = 0,
    val isBottomBarVisible: Boolean = true
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PdfUiState

        if (uri != other.uri) return false
        if (pdfData != null) {
            if (other.pdfData == null) return false
            if (!pdfData.contentEquals(other.pdfData)) return false
        } else if (other.pdfData != null) return false
        if (isLoading != other.isLoading) return false
        if (errorMessage != other.errorMessage) return false
        if (pageCount != other.pageCount) return false
        if (currentPage != other.currentPage) return false
        if (isBottomBarVisible != other.isBottomBarVisible) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uri?.hashCode() ?: 0
        result = 31 * result + (pdfData?.contentHashCode() ?: 0)
        result = 31 * result + isLoading.hashCode()
        result = 31 * result + (errorMessage?.hashCode() ?: 0)
        result = 31 * result + pageCount
        result = 31 * result + currentPage
        result = 31 * result + isBottomBarVisible.hashCode()
        return result
    }
}

@HiltViewModel
class PdfViewModel @Inject constructor(
    private val pdfDao: PdfDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(PdfUiState())
    val uiState: StateFlow<PdfUiState> = _uiState

    init {
        loadLastPdf()
    }

    private fun loadLastPdf() {
        viewModelScope.launch {
            val settings = pdfDao.getSettings().first()
            val uri = settings?.lastUri
            val page = settings?.lastPage ?: 0
            if (uri != null) {
                setUri(uri.toUri())
                _uiState.update { it.copy(currentPage = page) }
            }
        }
    }

    fun setUri(uri: Uri?) {
        if (uri == null) return
        _uiState.update { it.copy(uri = uri, isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            val current = pdfDao.getSettings().first()
            pdfDao.saveSettings(
                PdfEntity(
                    lastUri = uri.toString(), lastPage = current?.lastPage ?: 0
                )
            )
        }

        if (uri.scheme?.startsWith("http") == true) {
            downloadPdf(uri.toString())
        } else {
            // For local URIs, we might not need to read bytes if PDFView can handle it directly,
            // but for consistency we keep it simple.
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private fun downloadPdf(url: String) {
        viewModelScope.launch {
            val result = doDownload(url)
            _uiState.update { state ->
                when (result) {
                    is ByteArray -> state.copy(pdfData = result, isLoading = false)
                    is String -> state.copy(errorMessage = result, isLoading = false)
                    else -> state.copy(errorMessage = "Unknown error", isLoading = false)
                }
            }
        }
    }

    private suspend fun doDownload(url: String): Any = withContext(Dispatchers.IO) {
        var httpConnection: HttpURLConnection? = null
        try {
            httpConnection = URL(url).openConnection() as HttpURLConnection
            httpConnection.connect()
            val responseCode = httpConnection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                httpConnection.inputStream.readBytes()
            } else {
                "Error: $responseCode"
            }
        } catch (e: SSLException) {
            "SSL Error: ${e.message}"
        } catch (e: IOException) {
            "Download Error: ${e.message}"
        } finally {
            httpConnection?.disconnect()
        }
    }

    fun onPageChange(page: Int, pageCount: Int) {
        _uiState.update { it.copy(currentPage = page, pageCount = pageCount) }
        viewModelScope.launch {
            val current = pdfDao.getSettings().first()
            pdfDao.saveSettings(PdfEntity(lastUri = current?.lastUri, lastPage = page))
        }
    }

    fun toggleBottomBar() {
        _uiState.update { it.copy(isBottomBarVisible = !_uiState.value.isBottomBarVisible) }
    }

    fun onError(t: Throwable) {
        _uiState.update {
            it.copy(
                errorMessage = t.message ?: "Failed to load PDF", isLoading = false
            )
        }
    }
}