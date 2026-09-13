package com.flatcode.simplecomposeapps.wordpress.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.wordpress.data.WordpressRepository
import com.flatcode.simplecomposeapps.wordpress.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

data class WordpressUiState(
    val posts: List<Post> = emptyList(),
    val favoritePosts: List<Post> = emptyList(),
    val selectedPost: Post? = null,
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class WordpressViewModel @Inject constructor(
    application: Application,
    private val repository: WordpressRepository
) : AndroidViewModel(application) {

    private val _uiState = MutableLiveData(WordpressUiState())
    val uiState: LiveData<WordpressUiState> = _uiState

    init {
        observePosts()
        loadPosts()
    }

    private fun observePosts() {
        viewModelScope.launch {
            repository.getAllPosts().collectLatest { posts ->
                val currentState = _uiState.value ?: WordpressUiState()
                _uiState.postValue(currentState.copy(
                    posts = posts,
                    favoritePosts = posts.filter { p -> p.isFavorite },
                    isLoading = if (posts.isNotEmpty()) false else currentState.isLoading
                ))
            }
        }
    }

    fun loadPosts(withProgress: Boolean = true) {
        viewModelScope.launch {
            val currentState = _uiState.value ?: WordpressUiState()
            val hasData = currentState.posts.isNotEmpty()
            if (withProgress && !hasData) _uiState.value = currentState.copy(isLoading = true)
            else _uiState.value = currentState.copy(isRefreshing = true)

            try {
                repository.syncPosts()
                
                val updatedState = _uiState.value ?: WordpressUiState()
                _uiState.value = updatedState.copy(
                    isLoading = false,
                    isRefreshing = false,
                    errorMessage = null
                )
            } catch (_: Exception) {
                val updatedState = _uiState.value ?: WordpressUiState()
                _uiState.value = updatedState.copy(
                    isLoading = false,
                    isRefreshing = false,
                    errorMessage = if (updatedState.posts.isEmpty()) "No data available offline" else null
                )
            }
        }
    }

    fun selectPost(post: Post?) {
        val currentState = _uiState.value ?: WordpressUiState()
        _uiState.value = currentState.copy(selectedPost = post)
    }

    fun toggleFavorite(post: Post) {
        viewModelScope.launch {
            repository.toggleFavorite(post.id)
        }
    }
}