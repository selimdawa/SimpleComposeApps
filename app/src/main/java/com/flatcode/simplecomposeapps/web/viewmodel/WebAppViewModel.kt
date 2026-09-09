package com.flatcode.simplecomposeapps.web.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.web.data.WebDao
import com.flatcode.simplecomposeapps.web.data.WebEntity
import kotlinx.coroutines.flow.collectLatest
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
    private val webDao: WebDao
) : ViewModel() {

    private val _uiState = MutableLiveData(WebAppUiState())
    val uiState: LiveData<WebAppUiState> = _uiState

    init {
        observeHistory()
        observeBookmarks()
    }

    private fun observeHistory() {
        viewModelScope.launch {
            webDao.getItemsByType("HISTORY").collectLatest { list ->
                val currentState = _uiState.value ?: WebAppUiState()
                _uiState.postValue(currentState.copy(history = list))
            }
        }
    }

    private fun observeBookmarks() {
        viewModelScope.launch {
            webDao.getItemsByType("BOOKMARK").collectLatest { list ->
                val currentState = _uiState.value ?: WebAppUiState()
                _uiState.postValue(currentState.copy(bookmarks = list))
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
            val currentState = _uiState.value ?: WebAppUiState()
            val isBookmarked = currentState.bookmarks.any { it.url == url }
            if (isBookmarked) {
                currentState.bookmarks.find { it.url == url }?.let {
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

    fun setLoading(loading: Boolean) {
        val currentState = _uiState.value ?: WebAppUiState()
        _uiState.value = currentState.copy(isLoading = loading)
    }

    fun updateCurrentPage(title: String, url: String) {
        val currentState = _uiState.value ?: WebAppUiState()
        _uiState.value = currentState.copy(currentTitle = title, currentUrl = url)
    }

    fun showAboutDialog(show: Boolean) {
        val currentState = _uiState.value ?: WebAppUiState()
        _uiState.value = currentState.copy(showAboutDialog = show)
    }

    fun showSupportDialog(show: Boolean) {
        val currentState = _uiState.value ?: WebAppUiState()
        _uiState.value = currentState.copy(showSupportDialog = show)
    }
}