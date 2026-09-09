package com.flatcode.simplecomposeapps.randomcatsimage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.randomcatsimage.data.CatImageDao
import com.flatcode.simplecomposeapps.randomcatsimage.data.CatImageEntity
import com.flatcode.simplecomposeapps.randomcatsimage.network.CatImageApi
import com.flatcode.simplecomposeapps.utils.DATA
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomCatsImageViewModel @Inject constructor(
    private val api: CatImageApi,
    private val catImageDao: CatImageDao,
) : ViewModel() {

    private val _imageUrl = MutableLiveData("")
    val imageUrl: LiveData<String> = _imageUrl

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    private val _savedImages = MutableLiveData<List<String>>(emptyList())

    init {
        getImage()
        observeSavedImages()
    }

    private fun observeSavedImages() {
        viewModelScope.launch {
            catImageDao.getAllImages().collectLatest { entities ->
                val urls = entities.map { it.url }
                _savedImages.postValue(urls)
            }
        }
    }

    fun getImage() {
        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val response = api.getRandomImage()
                if (response.isNotEmpty()) {
                    val catUrl = response[0].url
                    _imageUrl.value = catUrl
                    _errorMessage.value = null
                    saveImage(catUrl)
                } else {
                    handleError()
                }
            } catch (_: Exception) {
                handleError()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun handleError() {
        val currentSaved = _savedImages.value ?: emptyList()
        if (currentSaved.isNotEmpty()) {
            _imageUrl.value = currentSaved.first()
            _errorMessage.value = null
        } else {
            val savedEntities = catImageDao.getAllImages().first()
            if (savedEntities.isNotEmpty()) {
                _imageUrl.value = savedEntities.first().url
                _errorMessage.value = null
            } else {
                _imageUrl.value = ""
                _errorMessage.value = DATA.FAILED_LOAD_DATA
            }
        }
    }

    private fun saveImage(url: String) {
        viewModelScope.launch {
            catImageDao.insertImage(CatImageEntity(url))
        }
    }
}