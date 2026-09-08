package com.flatcode.simplecomposeapps.wordpress.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.wordpress.data.PostDao
import com.flatcode.simplecomposeapps.wordpress.data.PostEntity
import com.flatcode.simplecomposeapps.wordpress.model.Rendered
import com.flatcode.simplecomposeapps.wordpress.model.Post
import com.flatcode.simplecomposeapps.wordpress.data.network.WordPressApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
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
    private val api: WordPressApi,
    private val postDao: PostDao
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(WordpressUiState())
    val uiState: StateFlow<WordpressUiState> = _uiState

    init {
        observePosts()
        viewModelScope.launch {
            // Wait for first Room emission to avoid "No data" flicker
            postDao.getAllPosts().first()
            loadPosts()
        }
    }

    private fun observePosts() {
        viewModelScope.launch {
            postDao.getAllPosts().collectLatest { entities ->
                val posts = entities.map { mapFromEntity(it) }
                _uiState.update { it.copy(
                    posts = posts,
                    favoritePosts = posts.filter { p -> p.isFavorite },
                    isLoading = if (posts.isNotEmpty()) false else it.isLoading
                ) }
            }
        }
    }


    private fun mapFromEntity(entity: PostEntity): Post {
        return Post(
            id = entity.wpPostId,
            title = Rendered(rendered = entity.wpTitle),
            excerpt = Rendered(rendered = entity.wpExcerpt),
            content = Rendered(rendered = entity.wpContent),
            featuredMedia = entity.featuredMedia,
            featuredMediaUrl = entity.featuredMediaUrl,
            isFavorite = entity.isFavorite
        )
    }

    private fun mapToEntity(post: Post, isFavorite: Boolean): PostEntity {
        return PostEntity(
            wpPostId = post.id,
            wpTitle = post.title?.rendered,
            wpExcerpt = post.excerpt?.rendered,
            wpContent = post.content?.rendered,
            featuredMedia = post.featuredMedia,
            featuredMediaUrl = post.featuredMediaUrl,
            isFavorite = isFavorite
        )
    }

    fun loadPosts(withProgress: Boolean = true) {
        viewModelScope.launch {
            val hasData = _uiState.value.posts.isNotEmpty()
            if (withProgress && !hasData) _uiState.update { it.copy(isLoading = true) }
            else _uiState.update { it.copy(isRefreshing = true) }

            try {
                val posts = api.getPosts()

                val favoriteIds = postDao.getFavoriteIds().toSet()

                // Fetch media URLs for each post in parallel
                val postsWithMedia = posts.map { post ->
                    async {
                        if (post.featuredMedia > 0) {
                            try {
                                val media = api.getPostThumbnail(post.featuredMedia)
                                post.copy(featuredMediaUrl = media.guid?.rendered)
                            } catch (_: Exception) {
                                post
                            }
                        } else {
                            post
                        }
                    }
                }.awaitAll()

                val entities = postsWithMedia.map { post ->
                    mapToEntity(post, favoriteIds.contains(post.id))
                }

                postDao.insertPosts(entities)
                
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isRefreshing = false,
                        errorMessage = null
                    )
                }
            } catch (_: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isRefreshing = false,
                        errorMessage = if (_uiState.value.posts.isEmpty()) "No data available offline" else null
                    )
                }
            }
        }
    }

    fun selectPost(post: Post?) {
        _uiState.update { it.copy(selectedPost = post) }
    }

    fun toggleFavorite(post: Post) {
        viewModelScope.launch {
            val isFav = postDao.isFavorite(post.id).first()
            postDao.updateFavorite(post.id, !isFav)
        }
    }
}