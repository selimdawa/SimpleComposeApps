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
import org.json.JSONException

class JokeViewModel(application: Application) : AndroidViewModel(application) {

    private val _jokes = mutableStateListOf<Joke>()
    val jokes: List<Joke> get() = _jokes

    private val _isLoading = mutableStateOf(value = false)
    val isLoading: State<Boolean> = _isLoading

    private val _selectedCategory = mutableStateOf(value = "Any")
    val selectedCategory: State<String> = _selectedCategory

    val categories = listOf("Any", "Programming", "Dark", "Spooky", "Misc", "Pun", "Christmas")

    init {
        getJokes("Any")
    }

    fun onCategorySelected(category: String) {
        _selectedCategory.value = category
        getJokes(category)
    }

    private fun getJokes(category: String) {
        val url = "https://v2.jokeapi.dev/joke/$category?amount=10"
        _isLoading.value = true
        _jokes.clear()

        val queue = Volley.newRequestQueue(getApplication())
        val objectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val jokesArray = response.getJSONArray("jokes")
                    val amount = response.optInt("amount", jokesArray.length())

                    for (i in 0 until amount) {
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
                        }
                        _jokes.add(jokeObject)
                    }
                } catch (_: JSONException) {
                } finally {
                    _isLoading.value = false
                }
            },
        ) {
            _isLoading.value = false
        }

        queue.add(objectRequest)
    }
}