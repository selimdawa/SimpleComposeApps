package com.flatcode.simplecomposeapps.randomcatsimage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.randomcatsimage.data.CatImageDao
import com.flatcode.simplecomposeapps.randomcatsimage.data.CatImageEntity
import com.flatcode.simplecomposeapps.randomcatsimage.network.CatImageApi
import com.flatcode.simplecomposeapps.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomCatsImageViewModel @Inject constructor(
    private val api: CatImageApi,
    private val catImageDao: CatImageDao,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _imageUrl = MutableLiveData("")
    val imageUrl: LiveData<String> = _imageUrl

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

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
                if ((_imageUrl.value ?: "").isEmpty() && urls.isNotEmpty() && !networkHelper.isNetworkConnected()) {
                    _imageUrl.postValue(urls.random())
                }
            }
        }
    }

    fun getImage() {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = api.getRandomImage()
                if (response.isNotEmpty()) {
                    val catUrl = response[0].url
                    _imageUrl.value = catUrl
                    saveImage(catUrl)
                }
            } catch (_: Exception) {
                val currentSaved = _savedImages.value ?: emptyList()
                if ((_imageUrl.value ?: "").isEmpty() && currentSaved.isNotEmpty()) {
                    _imageUrl.value = currentSaved.random()
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun saveImage(url: String) {
        viewModelScope.launch {
            catImageDao.insertImage(CatImageEntity(url))
        }
    }
}