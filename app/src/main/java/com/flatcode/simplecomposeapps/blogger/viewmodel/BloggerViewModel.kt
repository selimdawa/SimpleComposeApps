package com.flatcode.simplecomposeapps.blogger.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.blogger.data.network.BloggerApi
import com.flatcode.simplecomposeapps.blogger.model.Comment
import com.flatcode.simplecomposeapps.blogger.model.Label
import com.flatcode.simplecomposeapps.blogger.model.Page
import com.flatcode.simplecomposeapps.blogger.model.Post
import com.flatcode.simplecomposeapps.utils.DATA
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BloggerViewModel @Inject constructor(
    private val api: BloggerApi
) : ViewModel() {

    val posts = mutableStateListOf<Post>()

    val pages = mutableStateListOf<Page>()

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val nextPageToken = mutableStateOf(DATA.EMPTY)

    val hasMore: Boolean get() = nextPageToken.value != "end"

    private val _details = mutableStateOf<Post?>(null)
    val details: State<Post?> = _details

    val labels = mutableStateListOf<Label>()

    val comments = mutableStateListOf<Comment>()

    private var currentQuery = DATA.EMPTY

    fun loadPosts(isLoadMore: Boolean = false) {
        if (isLoadMore && nextPageToken.value == "end") return
        if (!isLoadMore) {
            posts.clear()
            nextPageToken.value = DATA.EMPTY
            currentQuery = DATA.EMPTY
        }

        fetchPosts(isSearch = false)
    }

    fun searchPosts(query: String, isLoadMore: Boolean = false) {
        if (query.isEmpty()) {
            loadPosts()
            return
        }

        if (isLoadMore && nextPageToken.value == "end") return
        if (!isLoadMore) {
            posts.clear()
            nextPageToken.value = DATA.EMPTY
            currentQuery = query
        }

        fetchPosts(isSearch = true)
    }

    private fun fetchPosts(isSearch: Boolean) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = if (isSearch) {
                    api.searchPosts(
                        query = currentQuery,
                        pageToken = if (nextPageToken.value == DATA.EMPTY) null else nextPageToken.value
                    )
                } else {
                    api.getPosts(
                        pageToken = if (nextPageToken.value == DATA.EMPTY) null else nextPageToken.value
                    )
                }
                nextPageToken.value = response.nextPageToken ?: "end"
                response.items?.let { posts.addAll(it) }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadPages() {
        _isLoading.value = true
        pages.clear()
        viewModelScope.launch {
            try {
                val response = api.getPages()
                response.items?.let { pages.addAll(it) }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadPostDetails(postId: String) {
        _isLoading.value = true
        _details.value = null
        labels.clear()
        comments.clear()

        viewModelScope.launch {
            try {
                val post = api.getPostDetails(postId)
                _details.value = post
                post.labels?.forEach { labels.add(Label(it)) }
                loadComments(postId)
            } catch (e: Exception) {
                e.printStackTrace()
                _isLoading.value = false
            }
        }
    }

    fun loadPageDetails(pageId: String) {
        _isLoading.value = true
        _details.value = null
        labels.clear()
        comments.clear()

        viewModelScope.launch {
            try {
                _details.value = api.getPageDetails(pageId)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun loadComments(postId: String) {
        viewModelScope.launch {
            try {
                val response = api.getComments(postId)
                response.items?.forEach { item ->
                    val author = item.author
                    val image = author?.image?.url
                    comments.add(
                        Comment(
                            id = item.id ?: DATA.EMPTY,
                            name = author?.displayName ?: DATA.EMPTY,
                            profileImage = "https:$image",
                            published = item.published ?: DATA.EMPTY,
                            comment = item.content ?: DATA.EMPTY
                        )
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
