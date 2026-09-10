package com.flatcode.simplecomposeapps.rickAndMorty.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.rickAndMorty.model.Character
import com.flatcode.simplecomposeapps.rickAndMorty.data.RickAndMortyRepository
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RickCharactersViewModel @Inject constructor(
    private val repository: RickAndMortyRepository
) : ViewModel() {

    private val _characters = MutableLiveData<Resource<List<Character>>>(Resource.Loading())
    val characters: LiveData<Resource<List<Character>>> = _characters

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    private var currentPage = 1
    private var isLastPage = false
    private val allCharacters = mutableListOf<Character>()

    fun getCharacters() {
        if (isLastPage || (_isLoading.value ?: false)) return
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
                    
                    else -> {}
                }
            }
        }
    }
}