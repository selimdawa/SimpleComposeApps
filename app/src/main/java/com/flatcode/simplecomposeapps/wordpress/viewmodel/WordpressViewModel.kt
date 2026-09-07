package com.flatcode.simplecomposeapps.wordpress.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.wordpress.model.Post
import com.flatcode.simplecomposeapps.wordpress.sqlite.PostDB
import com.flatcode.simplecomposeapps.wordpress.utils.WordPressClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class WordpressUiState(
    val posts: List<Post> = emptyList(),
    val favoritePosts: List<Post> = emptyList(),
    val selectedPost: Post? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class WordpressViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    val uiState: StateFlow<WordpressUiState>
        field = MutableStateFlow(WordpressUiState())

    private val postDB = PostDB.getInstance(application)

    init {
        loadPosts()
        loadFavorites()
    }

    fun loadPosts(withProgress: Boolean = true) {
        viewModelScope.launch {
            if (withProgress) uiState.update { it.copy(isLoading = true) }
            else uiState.update { it.copy(isRefreshing = true) }

            try {
                val api = WordPressClient.apiService
                val response = api.getPosts()

                // Check each post if it is favorite
                val updatedPosts = response.map { post ->
                    post.copy(isFavorite = postDB?.getDbPostIsFav(post.id) == true)
                }

                uiState.update {
                    it.copy(
                        posts = updatedPosts,
                        isLoading = false,
                        isRefreshing = false,
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                uiState.update {
                    it.copy(
                        isLoading = false,
                        isRefreshing = false,
                        errorMessage = e.message
                    )
                }
            }
        }
    }

    fun loadFavorites() {
        viewModelScope.launch {
            val favorites = postDB?.allDbPosts ?: emptyList()
            uiState.update { it.copy(favoritePosts = favorites) }
        }
    }

    fun selectPost(post: Post?) {
        uiState.update { it.copy(selectedPost = post) }
    }

    fun toggleFavorite(post: Post) {
        viewModelScope.launch {
            postDB?.let { db ->
                if (db.getDbPostIsFav(post.id)) {
                    db.delete(post.id)
                } else {
                    db.insert(
                        wpPostID = post.id,
                        wpTitle = post.title?.rendered,
                        wpExcerpt = post.excerpt?.rendered,
                        wpContent = post.content?.rendered,
                        featuredMedia = post.featuredMedia,
                        isFavorite = true
                    )
                }
                loadFavorites()
                // Update post list if it contains this post
                uiState.update { state ->
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