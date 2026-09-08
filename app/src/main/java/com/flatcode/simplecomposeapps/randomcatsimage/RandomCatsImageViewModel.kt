package com.flatcode.simplecomposeapps.randomcatsimage

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.flatcode.simplecomposeapps.randomcatsimage.data.CatImageDao
import com.flatcode.simplecomposeapps.randomcatsimage.data.CatImageEntity
import com.flatcode.simplecomposeapps.utils.DATA
import com.flatcode.simplecomposeapps.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.json.JSONException
import javax.inject.Inject

@HiltViewModel
class RandomCatsImageViewModel @Inject constructor(
    application: Application,
    private val catImageDao: CatImageDao,
    private val networkHelper: NetworkHelper
) : AndroidViewModel(application) {

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
        val url = DATA.API_RANDOM_IMAGE
        _isLoading.value = true

        val queue = Volley.newRequestQueue(getApplication())
        val arrayRequest = JsonArrayRequest(Request.Method.GET, url, null, { response ->
            try {
                val kittyData = response.getJSONObject(0)
                val catUrl = kittyData.getString(DATA.URL)
                _imageUrl.value = catUrl
                saveImage(catUrl)
            } catch (_: JSONException) {
            } finally {
                _isLoading.value = false
            }
        }, {
            if (_imageUrl.value.isEmpty() && savedImages.isNotEmpty()) {
                _imageUrl.value = savedImages.random()
            }
            _isLoading.value = false
        })
        queue.add(arrayRequest)
    }

    private fun saveImage(url: String) {
        viewModelScope.launch {
            catImageDao.insertImage(CatImageEntity(url))
        }
    }
}