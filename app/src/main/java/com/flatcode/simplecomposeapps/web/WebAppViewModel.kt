package com.flatcode.simplecomposeapps.web

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.web.data.WebDao
import com.flatcode.simplecomposeapps.web.data.WebEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class WebAppUiState(
    val showAboutDialog: Boolean = false,
    val showSupportDialog: Boolean = false,
    val history: List<WebEntity> = emptyList(),
    val bookmarks: List<WebEntity> = emptyList()
)

@HiltViewModel
class WebAppViewModel @Inject constructor(
    private val webDao: WebDao
) : ViewModel() {

    val uiState: StateFlow<WebAppUiState>
        field = MutableStateFlow(WebAppUiState())

    init {
        observeHistory()
        observeBookmarks()
    }

    private fun observeHistory() {
        viewModelScope.launch {
            webDao.getItemsByType("HISTORY").collectLatest { list ->
                uiState.update { it.copy(history = list) }
            }
        }
    }

    private fun observeBookmarks() {
        viewModelScope.launch {
            webDao.getItemsByType("BOOKMARK").collectLatest { list ->
                uiState.update { it.copy(bookmarks = list) }
            }
        }
    }

    fun addHistory(title: String, url: String) {
        viewModelScope.launch {
            webDao.insertItem(WebEntity(title = title, url = url, type = "HISTORY"))
        }
    }

    fun addBookmark(title: String, url: String) {
        viewModelScope.launch {
            webDao.insertItem(WebEntity(title = title, url = url, type = "BOOKMARK"))
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

    fun showAboutDialog(show: Boolean) {
        uiState.update { it.copy(showAboutDialog = show) }
    }

    fun showSupportDialog(show: Boolean) {
        uiState.update { it.copy(showSupportDialog = show) }
    }
}