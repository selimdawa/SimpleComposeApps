package com.flatcode.simplecomposeapps.dictionary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.dictionary.data.repository.DictionaryRepository
import com.flatcode.simplecomposeapps.dictionary.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictionaryViewModel @Inject constructor(
    private val repository: DictionaryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<String>>(UiState.Idle)
    val uiState: StateFlow<UiState<String>> = _uiState.asStateFlow()

    fun searchWord(word: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val definition = repository.getWordDefinition(word)
                if (definition != "No definition found") {
                    _uiState.value = UiState.Success(definition)
                } else {
                    _uiState.value = UiState.Error("No definition found")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }
}