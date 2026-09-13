package com.flatcode.simplecomposeapps.randomcatsimage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.randomcatsimage.data.CatImageDao
import com.flatcode.simplecomposeapps.randomcatsimage.data.CatImageEntity
import com.flatcode.simplecomposeapps.randomcatsimage.network.CatImageApi
import com.flatcode.simplecomposeapps.ui.theme.Strings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RandomCatsImageViewModel @Inject constructor(
    private val api: CatImageApi,
    private val catImageDao: CatImageDao,
) : ViewModel() {

    private val _imageUrl = MutableStateFlow("")
    val imageUrl: StateFlow<String> = _imageUrl.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _savedImages = MutableStateFlow<List<String>>(emptyList())

    init {
        getImage()
        observeSavedImages()
    }

    private fun observeSavedImages() {
        viewModelScope.launch {
            catImageDao.getAllImages().collectLatest { entities ->
                val urls = entities.map { it.url }
                _savedImages.value = urls
            }
        }
    }

    fun getImage() {
        Timber.d("Requesting random cat image from API")
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
            } catch (e: Exception) {
                Timber.e(e, "Exception fetching random image")
                handleError()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun handleError() {
        val currentSaved = _savedImages.value
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
                _errorMessage.value = Strings.FAILED_LOAD_DATA
            }
        }
    }

    private fun saveImage(url: String) {
        viewModelScope.launch {
            catImageDao.insertImage(CatImageEntity(url))
        }
    }
}