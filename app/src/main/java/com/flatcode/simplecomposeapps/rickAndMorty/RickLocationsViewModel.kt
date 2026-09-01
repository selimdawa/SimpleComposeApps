package com.flatcode.simplecomposeapps.rickAndMorty

import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.rickAndMorty.data.models.Location
import com.flatcode.simplecomposeapps.rickAndMorty.data.models.RickAndMortyResponse
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

    private val _locations = MutableStateFlow<Resource<RickAndMortyResponse<Location>>>(Resource.Loading())
    val locations: StateFlow<Resource<RickAndMortyResponse<Location>>> = _locations.asStateFlow()

    fun getLocations(page: Int? = null) {
        viewModelScope.launch {
            repository.getLocations(page).collect {
                _locations.value = it
            }
        }
    }
}