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
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.DATA
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONException
import javax.inject.Inject

@HiltViewModel
class JokeViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    val jokes: List<Joke>
        field = mutableStateListOf<Joke>()

    val isLoading: State<Boolean>
        field = mutableStateOf(value = false)

    val errorMessage: State<String?>
        field = mutableStateOf<String?>(null)

    val selectedCategory: State<String>
        field = mutableStateOf(value = "Any")

    val categories = listOf("Any", Strings.PROGRAMMING, "Dark", "Spooky", "Misc", "Pun", "Christmas")

    init {
        getJokes("Any")
    }

    fun onCategorySelected(category: String) {
        selectedCategory.value = category
        // Mapping as per original code logic if needed, but JokeAPI supports all now.
        // The original code had: val endpoint = if (currentCategory == "Pun") "Programming" else currentCategory
        val endpoint = if (category == "Pun") Strings.PROGRAMMING else category
        getJokes(endpoint)
    }

    private fun getJokes(category: String) {
        val url = "${DATA.JOKE_URL}$category?amount=10"
        isLoading.value = true
        errorMessage.value = null
        jokes.clear()

        val queue = Volley.newRequestQueue(getApplication())
        val objectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    if (response.optBoolean("error")) {
                        errorMessage.value = response.optString("message", "Failed to fetch jokes")
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
                            jokes.add(jokeObject)
                        }
                    }
                } catch (_: JSONException) {
                    errorMessage.value = "Failed to parse jokes"
                } finally {
                    isLoading.value = false
                }
            },
            { error ->
                errorMessage.value = error.message ?: "Unknown error"
                isLoading.value = false
            }
        )
        queue.add(objectRequest)
    }
}