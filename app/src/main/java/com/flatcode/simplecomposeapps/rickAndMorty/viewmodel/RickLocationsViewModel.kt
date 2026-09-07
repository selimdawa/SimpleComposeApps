package com.flatcode.simplecomposeapps.rickAndMorty.viewmodel

import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.rickAndMorty.data.models.Location
import com.flatcode.simplecomposeapps.rickAndMorty.data.repositories.MainRepository
import com.flatcode.simplecomposeapps.rickAndMorty.ui.base.BaseViewModel
import com.flatcode.simplecomposeapps.rickAndMorty.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RickLocationsViewModel @Inject constructor(
    private val repository: MainRepository
) : BaseViewModel() {

    private val _locations = MutableStateFlow<Resource<List<Location>>>(Resource.Loading())
    val locations: StateFlow<Resource<List<Location>>> = _locations.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private var currentPage = 1
    private var isLastPage = false
    private val allLocations = mutableListOf<Location>()

    fun getLocations() {
        if (isLastPage || _isLoading.value) return
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            repository.getLocations(currentPage).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.let { response ->
                            allLocations.addAll(response.results)
                            isLastPage = response.info.next == null
                            _locations.value = Resource.Success(allLocations.toList())
                            currentPage++
                        }
                        _isLoading.value = false
                    }

                    is Resource.Error -> {
                        if (allLocations.isEmpty()) {
                            _locations.value = Resource.Error(resource.message ?: "Error")
                        } else {
                            _error.value = resource.message ?: "Error"
                        }
                        _isLoading.value = false
                    }

                    is Resource.Loading -> {
                        if (allLocations.isEmpty()) {
                            _locations.value = Resource.Loading()
                        }
                    }
                }
            }
        }
    }
}
