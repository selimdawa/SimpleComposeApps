package com.flatcode.simplecomposeapps.dictionary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.dictionary.data.repository.DictionaryRepository
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DictionaryViewModel @Inject constructor(
    private val repository: DictionaryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<Resource<String>>(Resource.Idle)
    val uiState: StateFlow<Resource<String>> = _uiState.asStateFlow()

    fun searchWord(word: String) {
        Timber.d("Searching word: %s", word)
        viewModelScope.launch {
            _uiState.value = Resource.Loading()
            val result = repository.getWordDefinition(word)
            _uiState.value = result
            if (result is Resource.Success) {
                Timber.d("Successfully found definition for: %s", word)
            } else if (result is Resource.Error) {
                Timber.e("Error searching word %s: %s", word, result.message)
            }
        }
    }
}
