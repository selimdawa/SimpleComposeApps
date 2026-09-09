package com.flatcode.simplecomposeapps.dogs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.dogs.data.DogRepository
import com.flatcode.simplecomposeapps.utils.NetworkHelper
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogViewModel @Inject constructor(
    private val repository: DogRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val _uiState = MutableLiveData<Resource<List<String>>>(Resource.Idle)
    val uiState: LiveData<Resource<List<String>>> = _uiState

    private val _breedsList = MutableLiveData<List<String>>(emptyList())
    val breedsList: LiveData<List<String>> = _breedsList

    val isNetworkAvailable = networkHelper.isNetworkAvailable

    fun setBreedsList(list: List<String>) {
        _breedsList.value = list
    }

    fun getDogPhotosList(breed: String) {
        viewModelScope.launch {
            repository.getDogsByBreed(breed, networkHelper.isNetworkConnected())
                .collect { resource ->
                    _uiState.value = resource
                }
        }
    }
}