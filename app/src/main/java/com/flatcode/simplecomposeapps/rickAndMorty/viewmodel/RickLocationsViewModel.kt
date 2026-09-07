package com.flatcode.simplecomposeapps.rickAndMorty.viewmodel

import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.rickAndMorty.data.models.Location
import com.flatcode.simplecomposeapps.rickAndMorty.data.repositories.MainRepository
import com.flatcode.simplecomposeapps.rickAndMorty.ui.base.BaseViewModel
import com.flatcode.simplecomposeapps.rickAndMorty.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RickLocationsViewModel @Inject constructor(
    private val repository: MainRepository
) : BaseViewModel() {

    val locations: StateFlow<Resource<List<Location>>>
        field = MutableStateFlow<Resource<List<Location>>>(Resource.Loading())

    val isLoading: StateFlow<Boolean>
        field = MutableStateFlow(false)

    val error: StateFlow<String?>
        field = MutableStateFlow<String?>(null)

    private var currentPage = 1
    private var isLastPage = false
    private val allLocations = mutableListOf<Location>()

    fun getLocations() {
        if (isLastPage || isLoading.value) return
        isLoading.value = true
        error.value = null

        viewModelScope.launch {
            repository.getLocations(currentPage).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.let { response ->
                            allLocations.addAll(response.results)
                            isLastPage = response.info.next == null
                            locations.value = Resource.Success(allLocations.toList())
                            currentPage++
                        }
                        isLoading.value = false
                    }

                    is Resource.Error -> {
                        if (allLocations.isEmpty()) {
                            locations.value = Resource.Error(resource.message ?: "Error")
                        } else {
                            error.value = resource.message ?: "Error"
                        }
                        isLoading.value = false
                    }

                    is Resource.Loading -> {
                        if (allLocations.isEmpty()) {
                            locations.value = Resource.Loading()
                        }
                    }
                }
            }
        }
    }
}
