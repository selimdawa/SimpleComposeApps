package com.flatcode.simplecomposeapps.joke.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.joke.data.network.JokeApi
import com.flatcode.simplecomposeapps.joke.model.Joke
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.DATA
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JokeViewModel @Inject constructor(
    private val api: JokeApi
) : ViewModel() {

    val jokes: List<Joke>
        field = mutableStateListOf<Joke>()

    val isLoading: State<Boolean>
        field = mutableStateOf(value = false)

    val errorMessage: State<String?>
        field = mutableStateOf<String?>(null)

    val selectedCategory: State<String>
        field = mutableStateOf(value = "Any")

    val categories = DATA.JOKE_CATEGORIES

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
        isLoading.value = true
        errorMessage.value = null
        jokes.clear()

        viewModelScope.launch {
            try {
                val response = api.getJokes(category)
                if (response.error) {
                    errorMessage.value = response.message ?: "Failed to fetch jokes"
                } else {
                    response.jokes?.let { jokes.addAll(it) }
                }
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "Unknown error"
            } finally {
                isLoading.value = false
            }
        }
    }
}
