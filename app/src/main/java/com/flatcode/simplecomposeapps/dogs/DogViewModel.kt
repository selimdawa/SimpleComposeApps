package com.flatcode.simplecomposeapps.dogs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.dogs.data.DogRepository
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogViewModel @Inject constructor(
    private val repository: DogRepository,
) : ViewModel() {

    private val _photos = MutableLiveData<List<String>>(emptyList())
    val photos: LiveData<List<String>> = _photos

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    private val _breedsList = MutableLiveData<List<String>>(emptyList())
    val breedsList: LiveData<List<String>> = _breedsList

    fun setBreedsList(list: List<String>) {
        _breedsList.value = list
    }

    fun getDogPhotosList(breed: String) {
        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            val result = repository.getDogsFromApi(breed)
            if (result is Resource.Success) {
                _photos.value = result.data ?: emptyList()
                _errorMessage.value = null
            } else {
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
        }
    }
}