package com.flatcode.simplecomposeapps.rickAndMorty.viewmodel

import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.rickAndMorty.data.models.Character
import com.flatcode.simplecomposeapps.rickAndMorty.data.repositories.MainRepository
import com.flatcode.simplecomposeapps.rickAndMorty.ui.base.BaseViewModel
import com.flatcode.simplecomposeapps.rickAndMorty.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RickCharactersViewModel @Inject constructor(
    private val repository: MainRepository
) : BaseViewModel() {

    private val _characters = MutableStateFlow<Resource<List<Character>>>(Resource.Loading())
    val characters: StateFlow<Resource<List<Character>>> = _characters

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private var currentPage = 1
    private var isLastPage = false
    private val allCharacters = mutableListOf<Character>()

    fun getCharacters() {
        if (isLastPage || _isLoading.value) return
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            repository.getCharacters(currentPage).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.let { response ->
                            allCharacters.addAll(response.results)
                            isLastPage = response.info.next == null
                            _characters.value = Resource.Success(allCharacters.toList())
                            currentPage++
                        }
                        _isLoading.value = false
                    }

                    is Resource.Error -> {
                        if (allCharacters.isEmpty()) {
                            _characters.value = Resource.Error(resource.message ?: "Error")
                        } else {
                            _error.value = resource.message ?: "Error"
                        }
                        _isLoading.value = false
                    }

                    is Resource.Loading -> {
                        if (allCharacters.isEmpty()) {
                            _characters.value = Resource.Loading()
                        }
                    }
                }
            }
        }
    }
}