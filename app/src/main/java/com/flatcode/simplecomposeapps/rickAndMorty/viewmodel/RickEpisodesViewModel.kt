package com.flatcode.simplecomposeapps.rickAndMorty.viewmodel

import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.rickAndMorty.data.models.Episode
import com.flatcode.simplecomposeapps.rickAndMorty.data.repositories.MainRepository
import com.flatcode.simplecomposeapps.rickAndMorty.ui.base.BaseViewModel
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RickEpisodesViewModel @Inject constructor(
    private val repository: MainRepository
) : BaseViewModel() {

    private val _episodes = MutableStateFlow<Resource<List<Episode>>>(Resource.Loading())
    val episodes: StateFlow<Resource<List<Episode>>> = _episodes

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private var currentPage = 1
    private var isLastPage = false
    private val allEpisodes = mutableListOf<Episode>()

    fun getEpisodes() {
        if (isLastPage || _isLoading.value) return
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            repository.getEpisodes(currentPage).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.let { response ->
                            allEpisodes.addAll(response.results)
                            isLastPage = response.info.next == null
                            _episodes.value = Resource.Success(allEpisodes.toList())
                            currentPage++
                        }
                        _isLoading.value = false
                    }

                    is Resource.Error -> {
                        if (allEpisodes.isEmpty()) {
                            _episodes.value = Resource.Error(resource.message ?: "Error")
                        } else {
                            _error.value = resource.message ?: "Error"
                        }
                        _isLoading.value = false
                    }

                    is Resource.Loading -> {
                        if (allEpisodes.isEmpty()) {
                            _episodes.value = Resource.Loading()
                        }
                    }
                    
                    else -> {}
                }
            }
        }
    }
}
