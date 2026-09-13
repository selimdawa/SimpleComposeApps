package com.flatcode.simplecomposeapps.web.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.web.data.WebEntity
import com.flatcode.simplecomposeapps.web.data.WebRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    val currentTitle: String = "",
    val selectedUrl: String? = null
)

@HiltViewModel
class WebAppViewModel @Inject constructor(
    private val repository: WebRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WebAppUiState())
    val uiState: StateFlow<WebAppUiState> = _uiState.asStateFlow()

    init {
        observeHistory()
        observeBookmarks()
    }

    private fun observeHistory() {
        viewModelScope.launch {
            repository.getItemsByType("HISTORY").collectLatest { list ->
                _uiState.update { it.copy(history = list) }
            }
        }
    }

    private fun observeBookmarks() {
        viewModelScope.launch {
            repository.getItemsByType("BOOKMARK").collectLatest { list ->
                _uiState.update { it.copy(bookmarks = list) }
            }
        }
    }

    private var lastAddedUrl: String? = null

    fun addHistory(title: String, url: String) {
        if (url.isBlank() || url == lastAddedUrl) return
        lastAddedUrl = url
        viewModelScope.launch {
            repository.insertItem(WebEntity(title = title.ifBlank { url }, url = url, type = "HISTORY"))
        }
    }

    fun toggleBookmark(title: String, url: String) {
        if (url.isBlank()) return
        viewModelScope.launch {
            val isBookmarked = _uiState.value.bookmarks.any { it.url == url }
            if (isBookmarked) {
                _uiState.value.bookmarks.find { it.url == url }?.let {
                    repository.deleteItem(it)
                }
            } else {
                repository.insertItem(
                    WebEntity(
                        title = title.ifBlank { url },
                        url = url,
                        type = "BOOKMARK"
                    )
                )
            }
        }
    }

    fun deleteItem(item: WebEntity) {
        viewModelScope.launch {
            repository.deleteItem(item)
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            repository.clearByType("HISTORY")
        }
    }

    fun clearBookmarks() {
        viewModelScope.launch {
            repository.clearByType("BOOKMARK")
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
}