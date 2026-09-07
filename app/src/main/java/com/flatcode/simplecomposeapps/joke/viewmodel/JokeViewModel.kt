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
import com.flatcode.simplecomposeapps.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.joke.data.JokeDao
import com.flatcode.simplecomposeapps.joke.data.JokeEntity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.json.JSONException
import javax.inject.Inject

@HiltViewModel
class JokeViewModel @Inject constructor(
    application: Application,
    private val jokeDao: JokeDao,
    private val networkHelper: NetworkHelper
) : AndroidViewModel(application) {

    val jokes = mutableStateListOf<Joke>()

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    private val _selectedCategory = mutableStateOf("Any")
    val selectedCategory: State<String> = _selectedCategory

    val categories = DATA.JOKE_CATEGORIES

    val savedJokes = mutableStateListOf<Joke>()

    init {
        getJokes("Any")
        observeSavedJokes()
    }

    private fun observeSavedJokes() {
        viewModelScope.launch {
            jokeDao.getAllJokes().collectLatest { entities ->
                savedJokes.clear()
                savedJokes.addAll(entities.map { mapFromEntity(it) })
            }
        }
    }

    private fun mapFromEntity(entity: JokeEntity): Joke {
        return Joke(
            id = entity.apiId,
            category = entity.category,
            type = entity.type,
            joke = entity.joke,
            setup = entity.setup,
            delivery = entity.delivery
        )
    }

    private fun mapToEntity(joke: Joke): JokeEntity {
        return JokeEntity(
            apiId = joke.id,
            category = joke.category,
            type = joke.type,
            joke = joke.joke,
            setup = joke.setup,
            delivery = joke.delivery
        )
    }

    fun saveJoke(joke: Joke) {
        viewModelScope.launch {
            jokeDao.insertJoke(mapToEntity(joke))
        }
    }

    fun onCategorySelected(category: String) {
        _selectedCategory.value = category
        getJokes(category)
    }

    fun retry() {
        getJokes(_selectedCategory.value)
    }

    private fun getJokes(category: String) {
        _isLoading.value = true
        _errorMessage.value = null
        jokes.clear()

        if (!networkHelper.isNetworkConnected()) {
            loadFromCache(category)
            return
        }

        val url = "${DATA.JOKE_URL}$category?amount=10"
        val queue = Volley.newRequestQueue(getApplication())
        val objectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                viewModelScope.launch {
                    try {
                        if (response.optBoolean(DATA.ERROR)) {
                            _errorMessage.value = response.optString(DATA.MESSAGE, "Failed to fetch jokes")
                            loadFromCache(category, true)
                        } else {
                            val jokesArray = response.getJSONArray(DATA.JOKES)
                            val newJokes = mutableListOf<Joke>()
                            val entities = mutableListOf<JokeEntity>()
                            
                            for (i in 0 until jokesArray.length()) {
                                val jokeData = jokesArray.getJSONObject(i)
                                val jokeType = jokeData.optString(DATA.TYPE)

                                val jokeObject = Joke().apply {
                                    id = jokeData.optInt(DATA.ID)
                                    type = jokeType
                                    if (jokeType == DATA.SINGLE) {
                                        joke = jokeData.optString(DATA.JOKE_KEY)
                                    } else {
                                        setup = jokeData.optString(DATA.SETUP)
                                        delivery = jokeData.optString(DATA.DELIVERY)
                                    }
                                    this.category = jokeData.optString(DATA.CATEGORY)
                                }
                                newJokes.add(jokeObject)
                                entities.add(mapToEntity(jokeObject).copy(isCache = true, cachedCategory = category))
                            }

                            jokeDao.deleteCacheByCategory(category)
                            jokeDao.insertJokes(entities)

                            jokes.clear()
                            jokes.addAll(newJokes)
                            _isLoading.value = false
                        }
                    } catch (e: JSONException) {
                        _errorMessage.value = "Failed to parse jokes: ${e.message}"
                        loadFromCache(category, true)
                    }
                }
            },
            { error ->
                val errorMsg = when {
                    error.networkResponse != null -> "Server error: ${error.networkResponse.statusCode}"
                    error.message?.contains("UnknownHostException") == true -> "No Internet Connection"
                    else -> error.message ?: "Network error"
                }
                _errorMessage.value = errorMsg
                loadFromCache(category, true)
            }
        )
        queue.add(objectRequest)
    }

    private fun loadFromCache(category: String, updateLoading: Boolean = true) {
        viewModelScope.launch {
            val cache = jokeDao.getCacheByCategory(category)
            if (cache.isNotEmpty()) {
                jokes.clear()
                jokes.addAll(cache.map { mapFromEntity(it) })
            } else {
                _errorMessage.value = Strings.NO_INFORMATION
            }
            if (updateLoading) {
                _isLoading.value = false
            }
        }
    }
}