package com.flatcode.simplecomposeapps.blogger.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.blogger.data.repository.BloggerRepository
import com.flatcode.simplecomposeapps.blogger.model.Comment
import com.flatcode.simplecomposeapps.blogger.model.Label
import com.flatcode.simplecomposeapps.blogger.model.Page
import com.flatcode.simplecomposeapps.blogger.model.Post
import com.flatcode.simplecomposeapps.utils.DATA
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BloggerViewModel @Inject constructor(
    private val repository: BloggerRepository
) : ViewModel() {

    private val _posts = MutableLiveData<List<Post>>(emptyList())
    val posts: LiveData<List<Post>> = _posts

    private val _pages = MutableLiveData<List<Page>>(emptyList())
    val pages: LiveData<List<Page>> = _pages

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _nextPageToken = MutableLiveData(DATA.EMPTY)
    val hasMore: Boolean get() = _nextPageToken.value != "end"

    private val _details = MutableLiveData<Post?>(null)
    val details: LiveData<Post?> = _details

    private val _labels = MutableLiveData<List<Label>>(emptyList())
    val labels: LiveData<List<Label>> = _labels

    private val _comments = MutableLiveData<List<Comment>>(emptyList())
    val comments: LiveData<List<Comment>> = _comments

    private var currentQuery = DATA.EMPTY

    fun loadPosts(isLoadMore: Boolean = false) {
        if (isLoadMore && _nextPageToken.value == "end") return
        if (!isLoadMore) {
            _posts.value = emptyList()
            _nextPageToken.value = DATA.EMPTY
            currentQuery = DATA.EMPTY
        }

        fetchPosts(isSearch = false)
    }

    fun searchPosts(query: String, isLoadMore: Boolean = false) {
        if (query.isEmpty()) {
            loadPosts()
            return
        }

        if (isLoadMore && _nextPageToken.value == "end") return
        if (!isLoadMore) {
            _posts.value = emptyList()
            _nextPageToken.value = DATA.EMPTY
            currentQuery = query
        }

        fetchPosts(isSearch = true)
    }

    private fun fetchPosts(isSearch: Boolean) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = if (isSearch) {
                repository.searchPosts(
                    query = currentQuery,
                    pageToken = if (_nextPageToken.value == DATA.EMPTY) null else _nextPageToken.value
                )
            } else {
                repository.getPosts(
                    pageToken = if (_nextPageToken.value == DATA.EMPTY) null else _nextPageToken.value
                )
            }

            when (result) {
                is Resource.Success -> {
                    val response = result.data
                    _nextPageToken.value = response?.nextPageToken ?: "end"
                    val currentList = _posts.value ?: emptyList()
                    val newList = response?.items ?: emptyList()
                    _posts.value = currentList + newList
                }
                is Resource.Error -> {
                    // Handle error
                }
                else -> {}
            }
            _isLoading.value = false
        }
    }

    fun loadPages() {
        _isLoading.value = true
        viewModelScope.launch {
            val result = repository.getPages()
            if (result is Resource.Success) {
                _pages.value = result.data ?: emptyList()
            }
            _isLoading.value = false
        }
    }

    fun loadPostDetails(postId: String) {
        _isLoading.value = true
        _details.value = null
        _labels.value = emptyList()
        _comments.value = emptyList()

        viewModelScope.launch {
            val result = repository.getPostDetails(postId)
            if (result is Resource.Success) {
                val post = result.data
                _details.value = post
                _labels.value = post?.labels?.map { Label(it) } ?: emptyList()
                loadComments(postId)
            } else {
                _isLoading.value = false
            }
        }
    }

    fun loadPageDetails(pageId: String) {
        _isLoading.value = true
        _details.value = null
        _labels.value = emptyList()
        _comments.value = emptyList()

        viewModelScope.launch {
            val result = repository.getPageDetails(pageId)
            if (result is Resource.Success) {
                val page = result.data
                _details.value = page?.let {
                    Post(
                        author = it.author,
                        content = it.content,
                        id = it.id,
                        published = it.published,
                        selfLink = it.selfLink,
                        title = it.title,
                        updated = it.updated,
                        url = it.url
                    )
                }
            }
            _isLoading.value = false
        }
    }

    private fun loadComments(postId: String) {
        viewModelScope.launch {
            val result = repository.getComments(postId)
            if (result is Resource.Success) {
                _comments.value = result.data?.map { item ->
                    val author = item.author
                    val image = author?.image?.url
                    Comment(
                        id = item.id ?: DATA.EMPTY,
                        name = author?.displayName ?: DATA.EMPTY,
                        profileImage = "https:$image",
                        published = item.published ?: DATA.EMPTY,
                        comment = item.content ?: DATA.EMPTY
                    )
                } ?: emptyList()
            }
            _isLoading.value = false
        }
    }
}