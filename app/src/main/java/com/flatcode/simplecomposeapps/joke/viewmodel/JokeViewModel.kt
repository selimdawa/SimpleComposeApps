package com.flatcode.simplecomposeapps.joke.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.joke.network.JokeApi
import com.flatcode.simplecomposeapps.joke.model.Joke
import com.flatcode.simplecomposeapps.joke.data.JokeDao
import com.flatcode.simplecomposeapps.joke.data.JokeEntity
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.DATA
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JokeViewModel @Inject constructor(
    private val api: JokeApi,
    private val jokeDao: JokeDao
) : ViewModel() {

    private val _jokes = MutableLiveData<List<Joke>>(emptyList())
    val jokes: LiveData<List<Joke>> = _jokes

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    private val _selectedCategory = MutableLiveData("Any")
    val selectedCategory: LiveData<String> = _selectedCategory

    val categories = DATA.JOKE_CATEGORIES

    init {
        getJokes("Any")
    }

    fun onCategorySelected(category: String) {
        _selectedCategory.value = category
        val endpoint = if (category == "Pun") Strings.PROGRAMMING else category
        getJokes(endpoint)
    }

    private fun getJokes(category: String) {
        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val response = api.getJokes(category)
                if (response.error) {
                    loadFromDb(category)
                } else {
                    val jokeList = response.jokes ?: emptyList()
                    if (jokeList.isNotEmpty()) {
                        _jokes.value = jokeList
                        _errorMessage.value = null
                        jokeDao.deleteJokesByCategory(category)
                        jokeDao.insertJokes(jokeList.map { it.toEntity(category) })
                    } else {
                        loadFromDb(category)
                    }
                }
            } catch (_: Exception) {
                loadFromDb(category)
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun loadFromDb(category: String) {
        val cachedJokes = jokeDao.getJokesByCategory(category)
        if (cachedJokes.isNotEmpty()) {
            _jokes.value = cachedJokes.map { it.toDomain() }
            _errorMessage.value = null
        } else {
            _jokes.value = emptyList()
            _errorMessage.value = Strings.NO_DATA_FOUND
        }
    }

    private fun Joke.toEntity(requestedCategory: String): JokeEntity {
        return JokeEntity(
            requestedCategory = requestedCategory,
            category = category,
            type = type,
            joke = joke,
            setup = setup,
            delivery = delivery
        )
    }

    private fun JokeEntity.toDomain(): Joke {
        return Joke(
            category = category,
            type = type,
            joke = joke,
            setup = setup,
            delivery = delivery
        )
    }
}
