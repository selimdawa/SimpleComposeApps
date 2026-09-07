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

    val characters: StateFlow<Resource<List<Character>>>
        field = MutableStateFlow<Resource<List<Character>>>(Resource.Loading())

    val isLoading: StateFlow<Boolean>
        field = MutableStateFlow(false)

    val error: StateFlow<String?>
        field = MutableStateFlow<String?>(null)

    private var currentPage = 1
    private var isLastPage = false
    private val allCharacters = mutableListOf<Character>()

    fun getCharacters() {
        if (isLastPage || isLoading.value) return
        isLoading.value = true
        error.value = null

        viewModelScope.launch {
            repository.getCharacters(currentPage).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.let { response ->
                            allCharacters.addAll(response.results)
                            isLastPage = response.info.next == null
                            characters.value = Resource.Success(allCharacters.toList())
                            currentPage++
                        }
                        isLoading.value = false
                    }

                    is Resource.Error -> {
                        if (allCharacters.isEmpty()) {
                            characters.value = Resource.Error(resource.message ?: "Error")
                        } else {
                            error.value = resource.message ?: "Error"
                        }
                        isLoading.value = false
                    }

                    is Resource.Loading -> {
                        if (allCharacters.isEmpty()) {
                            characters.value = Resource.Loading()
                        }
                    }
                }
            }
        }
    }
}