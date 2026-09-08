package com.flatcode.simplecomposeapps.web

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.web.data.WebDao
import com.flatcode.simplecomposeapps.web.data.WebEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class WebAppUiState(
    val showAboutDialog: Boolean = false,
    val showSupportDialog: Boolean = false,
    val history: List<WebEntity> = emptyList(),
    val bookmarks: List<WebEntity> = emptyList(),
    val isLoading: Boolean = false,
    val currentUrl: String = "",
    val currentTitle: String = ""
)

@HiltViewModel
class WebAppViewModel @Inject constructor(
    private val webDao: WebDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(WebAppUiState())
    val uiState: StateFlow<WebAppUiState> = _uiState

    private val _reloadEvent = MutableSharedFlow<Unit>()
    val reloadEvent: SharedFlow<Unit> = _reloadEvent.asSharedFlow()

    init {
        observeHistory()
        observeBookmarks()
    }

    private fun observeHistory() {
        viewModelScope.launch {
            webDao.getItemsByType("HISTORY").collectLatest { list ->
                _uiState.update { it.copy(history = list) }
            }
        }
    }

    private fun observeBookmarks() {
        viewModelScope.launch {
            webDao.getItemsByType("BOOKMARK").collectLatest { list ->
                _uiState.update { it.copy(bookmarks = list) }
            }
        }
    }

    private var lastAddedUrl: String? = null

    fun addHistory(title: String, url: String) {
        if (url.isBlank() || url == lastAddedUrl) return
        lastAddedUrl = url
        viewModelScope.launch {
            webDao.insertItem(WebEntity(title = title.ifBlank { url }, url = url, type = "HISTORY"))
        }
    }

    fun toggleBookmark(title: String, url: String) {
        if (url.isBlank()) return
        viewModelScope.launch {
            val isBookmarked = uiState.value.bookmarks.any { it.url == url }
            if (isBookmarked) {
                uiState.value.bookmarks.find { it.url == url }?.let {
                    webDao.deleteItem(it)
                }
            } else {
                webDao.insertItem(WebEntity(title = title.ifBlank { url }, url = url, type = "BOOKMARK"))
            }
        }
    }

    fun deleteItem(item: WebEntity) {
        viewModelScope.launch {
            webDao.deleteItem(item)
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            webDao.clearByType("HISTORY")
        }
    }

    fun setLoading(loading: Boolean) {
        _uiState.update { it.copy(isLoading = loading) }
    }

    fun updateCurrentPage(title: String, url: String) {
        _uiState.update { it.copy(currentTitle = title, currentUrl = url) }
    }

    fun showAboutDialog(show: Boolean) {
        _uiState.update { it.copy(showAboutDialog = show) }
    }

    fun showSupportDialog(show: Boolean) {
        _uiState.update { it.copy(showSupportDialog = show) }
    }

    fun reload() {
        viewModelScope.launch {
            _reloadEvent.emit(Unit)
        }
    }
}