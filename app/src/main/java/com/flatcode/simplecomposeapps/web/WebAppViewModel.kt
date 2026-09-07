package com.flatcode.simplecomposeapps.web

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class WebAppUiState(
    val showAboutDialog: Boolean = false, val showSupportDialog: Boolean = false
)

@HiltViewModel
class WebAppViewModel @Inject constructor() : ViewModel() {

    val uiState: StateFlow<WebAppUiState>
        field = MutableStateFlow(WebAppUiState())

    fun showAboutDialog(show: Boolean) {
        uiState.update { it.copy(showAboutDialog = show) }
    }

    fun showSupportDialog(show: Boolean) {
        uiState.update { it.copy(showSupportDialog = show) }
    }
}