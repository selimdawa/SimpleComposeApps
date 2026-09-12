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

    private var allPosts = listOf<Post>()

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
            allPosts = emptyList()
            _nextPageToken.value = "1"
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
            allPosts = emptyList()
            _nextPageToken.value = "1"
            currentQuery = query
        }

        fetchPosts(isSearch = true)
    }

    fun filterPosts(query: String) {
        if (query.isEmpty()) {
            _posts.value = allPosts
        } else {
            _posts.value = allPosts.filter {
                it.title?.contains(query, ignoreCase = true) == true
            }
        }
    }

    private fun fetchPosts(isSearch: Boolean) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = if (isSearch) {
                repository.searchPosts(
                    query = currentQuery,
                    startIndex = if (_nextPageToken.value == DATA.EMPTY) "1" else _nextPageToken.value!!
                )
            } else {
                repository.getPosts(
                    startIndex = if (_nextPageToken.value == DATA.EMPTY) "1" else _nextPageToken.value!!
                )
            }

            when (result) {
                is Resource.Success -> {
                    val response = result.data
                    _nextPageToken.value = response?.nextPageToken ?: "end"
                    val newList = response?.items ?: emptyList()
                    allPosts = allPosts + newList
                    _posts.value = allPosts
                }
                is Resource.Error -> {
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
                    Comment(
                        id = item.id ?: DATA.EMPTY,
                        name = item.author?.displayName ?: DATA.EMPTY,
                        profileImage = "https://www.blogger.com/img/blogger-logotype-color-black-caps.png",
                        published = item.published ?: DATA.EMPTY,
                        comment = item.content ?: DATA.EMPTY
                    )
                } ?: emptyList()
            }
            _isLoading.value = false
        }
    }
}