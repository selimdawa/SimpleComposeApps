package com.flatcode.simplecomposeapps.dogs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.dogs.data.DogRepository
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DogViewModel @Inject constructor(
    private val repository: DogRepository,
) : ViewModel() {

    private val _photos = MutableStateFlow<List<String>>(emptyList())
    val photos: StateFlow<List<String>> = _photos.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _breedsList = MutableStateFlow<List<String>>(emptyList())
    val breedsList: StateFlow<List<String>> = _breedsList.asStateFlow()

    fun setBreedsList(list: List<String>) {
        _breedsList.value = list
    }

    fun getDogPhotosList(breed: String) {
        Timber.d("Fetching dog photos for breed: %s", breed)
        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            val result = repository.getDogsFromApi(breed)
            if (result is Resource.Success) {
                _photos.value = result.data ?: emptyList()
                _errorMessage.value = null
            } else {
                Timber.w("Failed fetching from API, trying DB cache for breed: %s", breed)
                loadFromDb(breed)
            }
            _isLoading.value = false
        }
    }

    private suspend fun loadFromDb(breed: String) {
        val cached = repository.getDogsFromDb(breed)
        if (cached.isNotEmpty()) {
            _photos.value = cached
            _errorMessage.value = null
        } else {
            _photos.value = emptyList()
            _errorMessage.value = Strings.FAILED_LOAD_DATA
            Timber.e("No dog photos found in DB cache for breed: %s", breed)
        }
    }
}