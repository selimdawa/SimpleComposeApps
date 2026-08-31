package com.flatcode.simplecomposeapps.wordpress.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.wordpress.model.Post
import com.flatcode.simplecomposeapps.wordpress.sqlite.PostDB
import com.flatcode.simplecomposeapps.wordpress.utils.WordPressClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class WordpressUiState(
    val posts: List<Post> = emptyList(),
    val favoritePosts: List<Post> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null
)

class WordpressViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(WordpressUiState())
    val uiState: StateFlow<WordpressUiState> = _uiState.asStateFlow()

    private val postDB = PostDB.getInstance(application)

    init {
        loadPosts()
        loadFavorites()
    }

    fun loadPosts(withProgress: Boolean = true) {
        viewModelScope.launch {
            if (withProgress) _uiState.update { it.copy(isLoading = true) }
            else _uiState.update { it.copy(isRefreshing = true) }

            try {
                val api = WordPressClient.apiService
                val response = api.getPosts()
                
                // Check each post if it is favorite
                val updatedPosts = response.map { post ->
                    post.copy(isFavorite = postDB?.getDbPostIsFav(post.id) == true)
                }

                _uiState.update { it.copy(posts = updatedPosts, isLoading = false, isRefreshing = false, errorMessage = null) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, isRefreshing = false, errorMessage = e.message) }
            }
        }
    }

    fun loadFavorites() {
        viewModelScope.launch {
            val favorites = postDB?.allDbPosts ?: emptyList()
            _uiState.update { it.copy(favoritePosts = favorites) }
        }
    }

    fun toggleFavorite(post: Post) {
        viewModelScope.launch {
            postDB?.let { db ->
                if (db.getDbPostIsFav(post.id)) {
                    db.delete(post.id)
                } else {
                    db.insert(post.id, post.title?.rendered, post.excerpt?.rendered, true)
                }
                loadFavorites()
                // Update post list if it contains this post
                _uiState.update { state ->
                    state.copy(
                        posts = state.posts.map {
                            if (it.id == post.id) it.copy(isFavorite = db.getDbPostIsFav(post.id)) else it
                        }
                    )
                }
            }
        }
    }
}
