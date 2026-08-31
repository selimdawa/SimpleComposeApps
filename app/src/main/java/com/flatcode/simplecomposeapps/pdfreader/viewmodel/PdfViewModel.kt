package com.flatcode.simplecomposeapps.pdfreader.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.SSLException

data class PdfUiState(
    val uri: Uri? = null,
    val pdfData: ByteArray? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val pageCount: Int = 0,
    val currentPage: Int = 0,
    val isBottomBarVisible: Boolean = true
)

class PdfViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PdfUiState())
    val uiState: StateFlow<PdfUiState> = _uiState.asStateFlow()

    fun setUri(uri: Uri?, context: Context) {
        if (uri == null) return
        _uiState.update { it.copy(uri = uri, isLoading = true, errorMessage = null) }
        
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
    }

    fun toggleBottomBar() {
        _uiState.update { it.copy(isBottomBarVisible = !it.isBottomBarVisible) }
    }

    fun onError(t: Throwable) {
        _uiState.update { it.copy(errorMessage = t.message ?: "Failed to load PDF", isLoading = false) }
    }
}
