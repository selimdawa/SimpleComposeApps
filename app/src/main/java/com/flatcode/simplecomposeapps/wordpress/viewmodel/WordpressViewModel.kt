package com.flatcode.simplecomposeapps.wordpress.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.wordpress.data.WordpressRepository
import com.flatcode.simplecomposeapps.wordpress.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
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

    private val _uiState = MutableStateFlow(WordpressUiState())
    val uiState: StateFlow<WordpressUiState> = _uiState.asStateFlow()

    init {
        observePosts()
        loadPosts()
    }

    private fun observePosts() {
        viewModelScope.launch {
            repository.getAllPosts().collectLatest { posts ->
                val currentState = _uiState.value
                _uiState.value = currentState.copy(
                    posts = posts,
                    favoritePosts = posts.filter { p -> p.isFavorite },
                    isLoading = if (posts.isNotEmpty()) false else currentState.isLoading
                )
            }
        }
    }

    fun loadPosts(withProgress: Boolean = true) {
        viewModelScope.launch {
            Timber.d("Loading Wordpress posts, progress: %b", withProgress)
            val currentState = _uiState.value
            val hasData = currentState.posts.isNotEmpty()
            if (withProgress && !hasData) _uiState.value = currentState.copy(isLoading = true)
            else _uiState.value = currentState.copy(isRefreshing = true)

            try {
                repository.syncPosts()
                
                val updatedState = _uiState.value
                _uiState.value = updatedState.copy(
                    isLoading = false,
                    isRefreshing = false,
                    errorMessage = null
                )
            } catch (e: Exception) {
                Timber.e(e, "Error syncing Wordpress posts")
                val updatedState = _uiState.value
                _uiState.value = updatedState.copy(
                    isLoading = false,
                    isRefreshing = false,
                    errorMessage = if (updatedState.posts.isEmpty()) "No data available offline" else null
                )
            }
        }
    }

    fun selectPost(post: Post?) {
        val currentState = _uiState.value
        _uiState.value = currentState.copy(selectedPost = post)
    }

    fun toggleFavorite(post: Post) {
        Timber.d("Toggling favorite for post: %d", post.id)
        viewModelScope.launch {
            repository.toggleFavorite(post.id)
        }
    }
}