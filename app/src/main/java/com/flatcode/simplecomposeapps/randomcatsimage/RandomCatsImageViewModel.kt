package com.flatcode.simplecomposeapps.randomcatsimage

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.flatcode.simplecomposeapps.utils.DATA
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONException
import javax.inject.Inject

@HiltViewModel
class RandomCatsImageViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    val imageUrl: State<String>
        field = mutableStateOf("")

    val isLoading: State<Boolean>
        field = mutableStateOf(false)

    init {
        getImage()
    }

    fun getImage() {
        val url = DATA.API_RANDOM_IMAGE
        isLoading.value = true

        val queue = Volley.newRequestQueue(getApplication())
        val arrayRequest = JsonArrayRequest(Request.Method.GET, url, null, { response ->
            try {
                val kittyData = response.getJSONObject(0)
                val catUrl = kittyData.getString(DATA.JSON_URL)
                imageUrl.value = catUrl
            } catch (_: JSONException) {
            } finally {
                isLoading.value = false
            }
        }, {
            isLoading.value = false
        })
        queue.add(arrayRequest)
    }
}