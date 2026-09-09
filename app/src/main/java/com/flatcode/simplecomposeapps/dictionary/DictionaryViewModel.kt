package com.flatcode.simplecomposeapps.dictionary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.dictionary.data.repository.DictionaryRepository
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictionaryViewModel @Inject constructor(
    private val repository: DictionaryRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<Resource<String>>(Resource.Idle)
    val uiState: LiveData<Resource<String>> = _uiState

    fun searchWord(word: String) {
        viewModelScope.launch {
            _uiState.value = Resource.Loading()
            _uiState.value = repository.getWordDefinition(word)
        }
    }
}