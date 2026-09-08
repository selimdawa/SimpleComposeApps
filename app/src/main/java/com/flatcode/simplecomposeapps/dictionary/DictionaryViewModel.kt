package com.flatcode.simplecomposeapps.dictionary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.dictionary.data.repository.DictionaryRepository
import com.flatcode.simplecomposeapps.utils.Resource
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

    private val _uiState = MutableStateFlow<Resource<String>>(Resource.Idle)
    val uiState: StateFlow<Resource<String>> = _uiState

    fun searchWord(word: String) {
        viewModelScope.launch {
            _uiState.value = Resource.Loading()
            try {
                val definition = repository.getWordDefinition(word)
                if (definition != "No definition found") {
                    _uiState.value = Resource.Success(definition)
                } else {
                    _uiState.value = Resource.Error("No definition found")
                }
            } catch (e: Exception) {
                _uiState.value = Resource.Error(e.message ?: Strings.UNKNOWN_ERROR)
            }
        }
    }
}