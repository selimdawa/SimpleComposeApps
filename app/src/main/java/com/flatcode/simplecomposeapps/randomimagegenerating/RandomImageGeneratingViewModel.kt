package com.flatcode.simplecomposeapps.randomimagegenerating

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.flatcode.simplecomposeapps.utils.DATA
import org.json.JSONException

class RandomImageGeneratingViewModel(application: Application) : AndroidViewModel(application) {

    private val _imageUrl = mutableStateOf("")
    val imageUrl: State<String> = _imageUrl

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    private val _catBreedInfo = mutableStateOf<CatBreedInfo?>(null)
    val catBreedInfo: State<CatBreedInfo?> = _catBreedInfo

    init {
        getImage()
    }

    fun getImage() {
        val url = DATA.API_RANDOM_IMAGE
        _isLoading.value = true
        _errorMessage.value = null

        val queue = Volley.newRequestQueue(getApplication())
        val arrayRequest = JsonArrayRequest(Request.Method.GET, url, null, { response ->
            try {
                val kittyData = response.getJSONObject(0)
                val catUrl = kittyData.getString(DATA.JSON_URL)
                _imageUrl.value = catUrl

                try {
                    val breedsInfo = kittyData.getJSONArray(DATA.JSON_BREEDS)
                    if (!breedsInfo.isNull(0)) {
                        val breedsData = breedsInfo.getJSONObject(0)
                        _catBreedInfo.value = CatBreedInfo(
                            name = if (breedsData.has(DATA.JSON_NAME)) breedsData.getString(DATA.JSON_NAME) else DATA.EMPTY,
                            origin = if (breedsData.has(DATA.JSON_ORIGIN)) breedsData.getString(DATA.JSON_ORIGIN) else DATA.EMPTY,
                            description = if (breedsData.has(DATA.JSON_DESCRIPTION)) breedsData.getString(DATA.JSON_DESCRIPTION) else DATA.EMPTY,
                            temperament = if (breedsData.has(DATA.JSON_TEMPERAMENT)) breedsData.getString(DATA.JSON_TEMPERAMENT) else DATA.EMPTY,
                            wikiUrl = if (breedsData.has(DATA.JSON_WIKIPEDIA_URL)) breedsData.getString(DATA.JSON_WIKIPEDIA_URL) else DATA.EMPTY,
                            moreLink = if (breedsData.has(DATA.JSON_VCA_HOSPITALS_URL)) breedsData.getString(DATA.JSON_VCA_HOSPITALS_URL) else DATA.EMPTY,
                            imageUrl = catUrl
                        )
                    } else {
                        _catBreedInfo.value = null
                    }
                } catch (e: JSONException) {
                    _catBreedInfo.value = null
                }
            } catch (e: JSONException) {
                _errorMessage.value = "Failed to parse data"
            } finally {
                _isLoading.value = false
            }
        }, { error ->
            _errorMessage.value = error.message ?: "Unknown error"
            _isLoading.value = false
        })
        queue.add(arrayRequest)
    }
}

data class CatBreedInfo(
    val name: String,
    val origin: String,
    val description: String,
    val temperament: String,
    val wikiUrl: String,
    val moreLink: String,
    val imageUrl: String
)
