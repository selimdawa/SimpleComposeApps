package com.flatcode.simplecomposeapps.rickAndMorty.viewmodel

import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.rickAndMorty.data.models.Episode
import com.flatcode.simplecomposeapps.rickAndMorty.data.repositories.MainRepository
import com.flatcode.simplecomposeapps.rickAndMorty.ui.base.BaseViewModel
import com.flatcode.simplecomposeapps.rickAndMorty.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RickEpisodesViewModel @Inject constructor(
    private val repository: MainRepository
) : BaseViewModel() {

    val episodes: StateFlow<Resource<List<Episode>>>
        field = MutableStateFlow<Resource<List<Episode>>>(Resource.Loading())

    val isLoading: StateFlow<Boolean>
        field = MutableStateFlow(false)

    val error: StateFlow<String?>
        field = MutableStateFlow<String?>(null)

    private var currentPage = 1
    private var isLastPage = false
    private val allEpisodes = mutableListOf<Episode>()

    fun getEpisodes() {
        if (isLastPage || isLoading.value) return
        isLoading.value = true
        error.value = null

        viewModelScope.launch {
            repository.getEpisodes(currentPage).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.let { response ->
                            allEpisodes.addAll(response.results)
                            isLastPage = response.info.next == null
                            episodes.value = Resource.Success(allEpisodes.toList())
                            currentPage++
                        }
                        isLoading.value = false
                    }

                    is Resource.Error -> {
                        if (allEpisodes.isEmpty()) {
                            episodes.value = Resource.Error(resource.message ?: "Error")
                        } else {
                            error.value = resource.message ?: "Error"
                        }
                        isLoading.value = false
                    }

                    is Resource.Loading -> {
                        if (allEpisodes.isEmpty()) {
                            episodes.value = Resource.Loading()
                        }
                    }
                }
            }
        }
    }
}
