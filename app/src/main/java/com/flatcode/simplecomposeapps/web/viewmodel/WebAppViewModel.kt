package com.flatcode.simplecomposeapps.web.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class WebAppUiState(
    val showAboutDialog: Boolean = false,
    val showSupportDialog: Boolean = false
)

@HiltViewModel
class WebAppViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(WebAppUiState())
    val uiState: StateFlow<WebAppUiState> = _uiState.asStateFlow()

    fun showAboutDialog(show: Boolean) {
        _uiState.update { it.copy(showAboutDialog = show) }
    }

    fun showSupportDialog(show: Boolean) {
        _uiState.update { it.copy(showSupportDialog = show) }
    }
}
