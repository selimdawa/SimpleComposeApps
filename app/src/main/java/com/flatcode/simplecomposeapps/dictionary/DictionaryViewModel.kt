package com.flatcode.simplecomposeapps.dictionary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.dictionary.data.repository.DictionaryRepository
import com.flatcode.simplecomposeapps.dictionary.utils.UiState
import com.flatcode.simplecomposeapps.ui.theme.Strings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictionaryViewModel @Inject constructor(
    private val repository: DictionaryRepository
) : ViewModel() {

    val uiState: StateFlow<UiState<String>>
        field = MutableStateFlow<UiState<String>>(UiState.Idle)

    fun searchWord(word: String) {
        viewModelScope.launch {
            uiState.value = UiState.Loading
            try {
                val definition = repository.getWordDefinition(word)
                if (definition != "No definition found") {
                    uiState.value = UiState.Success(definition)
                } else {
                    uiState.value = UiState.Error("No definition found")
                }
            } catch (e: Exception) {
                uiState.value = UiState.Error(e.message ?: Strings.UNKNOWN_ERROR)
            }
        }
    }
}