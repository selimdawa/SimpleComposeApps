package com.flatcode.simplecomposeapps.rickAndMorty.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.rickAndMorty.model.Location
import com.flatcode.simplecomposeapps.rickAndMorty.data.RickAndMortyRepository
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RickLocationsViewModel @Inject constructor(
    private val repository: RickAndMortyRepository
) : ViewModel() {

    private val _locations = MutableLiveData<Resource<List<Location>>>(Resource.Loading())
    val locations: LiveData<Resource<List<Location>>> = _locations

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    private var currentPage = 1
    private var isLastPage = false
    private val allLocations = mutableListOf<Location>()

    fun getLocations() {
        if (isLastPage || (_isLoading.value ?: false)) return
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
                    
                    else -> {}
                }
            }
        }
    }
}