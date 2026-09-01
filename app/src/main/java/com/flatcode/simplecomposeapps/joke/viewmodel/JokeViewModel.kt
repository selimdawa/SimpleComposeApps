package com.flatcode.simplecomposeapps.joke.viewmodel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.flatcode.simplecomposeapps.joke.model.Joke
import com.flatcode.simplecomposeapps.utils.DATA
import org.json.JSONException

class JokeViewModel(application: Application) : AndroidViewModel(application) {

    private val _jokes = mutableStateListOf<Joke>()
    val jokes: List<Joke> get() = _jokes

    private val _isLoading = mutableStateOf(value = false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    private val _selectedCategory = mutableStateOf(value = "Any")
    val selectedCategory: State<String> = _selectedCategory

    val categories = listOf("Any", "Programming", "Dark", "Spooky", "Misc", "Pun", "Christmas")

    init {
        getJokes("Any")
    }

    fun onCategorySelected(category: String) {
        _selectedCategory.value = category
        // Mapping as per original code logic if needed, but JokeAPI supports all now.
        // The original code had: val endpoint = if (currentCategory == "Pun") "Programming" else currentCategory
        val endpoint = if (category == "Pun") "Programming" else category
        getJokes(endpoint)
    }

    private fun getJokes(category: String) {
        val url = "${DATA.JOKE_URL}$category?amount=10"
        _isLoading.value = true
        _errorMessage.value = null
        _jokes.clear()

        val queue = Volley.newRequestQueue(getApplication())
        val objectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    if (response.optBoolean("error")) {
                        _errorMessage.value = response.optString("message", "Failed to fetch jokes")
                    } else {
                        val jokesArray = response.getJSONArray("jokes")
                        for (i in 0 until jokesArray.length()) {
                            val jokeData = jokesArray.getJSONObject(i)
                            val jokeType = jokeData.optString("type")

                            val jokeObject = Joke().apply {
                                type = jokeType
                                if (jokeType == "single") {
                                    joke = jokeData.optString("joke")
                                } else {
                                    setup = jokeData.optString("setup")
                                    delivery = jokeData.optString("delivery")
                                }
                                this.category = jokeData.optString("category")
                            }
                            _jokes.add(jokeObject)
                        }
                    }
                } catch (e: JSONException) {
                    _errorMessage.value = "Failed to parse jokes"
                } finally {
                    _isLoading.value = false
                }
            },
            { error ->
                _errorMessage.value = error.message ?: "Unknown error"
                _isLoading.value = false
            }
        )
        queue.add(objectRequest)
    }
}