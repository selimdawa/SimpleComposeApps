package com.flatcode.simplecomposeapps.randomcatsimage

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
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

    private val _imageUrl = mutableStateOf("")
    val imageUrl: State<String> = _imageUrl

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    val savedImages = mutableStateListOf<String>()

    init {
        getImage()
        observeSavedImages()
    }

    private fun observeSavedImages() {
        viewModelScope.launch {
            catImageDao.getAllImages().collectLatest { entities ->
                savedImages.clear()
                savedImages.addAll(entities.map { it.url })
                if (_imageUrl.value.isEmpty() && savedImages.isNotEmpty() && !networkHelper.isNetworkConnected()) {
                    _imageUrl.value = savedImages.random()
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
                if (_imageUrl.value.isEmpty() && savedImages.isNotEmpty()) {
                    _imageUrl.value = savedImages.random()
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