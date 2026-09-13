package com.flatcode.simplecomposeapps.blogger.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.blogger.model.Comment
import com.flatcode.simplecomposeapps.blogger.model.Label
import com.flatcode.simplecomposeapps.blogger.model.Page
import com.flatcode.simplecomposeapps.blogger.model.Post
import com.flatcode.simplecomposeapps.blogger.repository.BloggerRepository
import com.flatcode.simplecomposeapps.utils.DATA
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    private val _nextPageToken = MutableLiveData(DATA.EMPTY)
    val hasMore: Boolean get() = _nextPageToken.value != "end"

    private val _details = MutableLiveData<Post?>(null)
    val details: LiveData<Post?> = _details

    private val _labels = MutableLiveData<List<Label>>(emptyList())
    val labels: LiveData<List<Label>> = _labels

    private val _comments = MutableLiveData<List<Comment>>(emptyList())
    val comments: LiveData<List<Comment>> = _comments

    private var currentQuery = DATA.EMPTY

    private var detailsJob: Job? = null

    init {
        observeCachedData()
    }

    private fun observeCachedData() {
        viewModelScope.launch {
            repository.getCachedPosts().collect { list ->
                if (list.isNotEmpty() || _posts.value?.isEmpty() == true) {
                    _posts.value = list
                    allPosts = list
                }
            }
        }
        viewModelScope.launch {
            repository.getCachedPages().collect { list ->
                if (list.isNotEmpty() || _pages.value?.isEmpty() == true) {
                    _pages.value = list
                }
            }
        }
    }

    fun loadPosts(isLoadMore: Boolean = false) {
        if (isLoadMore && _nextPageToken.value == "end") return
        if (!isLoadMore) {
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
        _error.value = null
        val isFirstPage = _nextPageToken.value == "1" || _nextPageToken.value == DATA.EMPTY
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
                    allPosts = if (isFirstPage) newList else allPosts + newList
                    _posts.value = allPosts
                    repository.insertPosts(allPosts)
                }

                is Resource.Error -> {
                    _error.value = result.message
                }

                else -> {}
            }
            _isLoading.value = false
        }
    }

    fun loadPages() {
        _isLoading.value = true
        _error.value = null
        viewModelScope.launch {
            val result = repository.getPages()
            if (result is Resource.Success) {
                val data = result.data ?: emptyList()
                repository.insertPages(data)
            } else if (result is Resource.Error) {
                _error.value = result.message
            }
            _isLoading.value = false
        }
    }

    fun loadPostDetails(postId: String) {
        detailsJob?.cancel()
        _isLoading.value = true
        _error.value = null
        _details.value = null
        _labels.value = emptyList()
        _comments.value = emptyList()

        detailsJob = viewModelScope.launch {
            repository.getCachedPost(postId).collect { post ->
                if (post != null) {
                    _details.value = post
                    _labels.value = post.labels?.map { Label(it) } ?: emptyList()
                }
            }
        }

        viewModelScope.launch {
            val result = repository.getPostDetails(postId)
            if (result is Resource.Success) {
                val post = result.data
                if (post != null) {
                    repository.insertPosts(listOf(post))
                }
                loadComments(postId)
            } else {
                if (result is Resource.Error) {
                    _error.value = result.message
                }
                _isLoading.value = false
            }
        }
    }

    fun loadPageDetails(pageId: String) {
        detailsJob?.cancel()
        _isLoading.value = true
        _error.value = null
        _details.value = null
        _labels.value = emptyList()
        _comments.value = emptyList()

        detailsJob = viewModelScope.launch {
            repository.getCachedPage(pageId).collect { page ->
                if (page != null) {
                    _details.value = Post(
                        author = page.author,
                        content = page.content,
                        id = page.id,
                        published = page.published,
                        selfLink = page.selfLink,
                        title = page.title,
                        updated = page.updated,
                        url = page.url
                    )
                }
            }
        }

        viewModelScope.launch {
            val result = repository.getPageDetails(pageId)
            if (result is Resource.Success) {
                val page = result.data
                if (page != null) {
                    repository.insertPages(listOf(page))
                }
            } else {
                if (result is Resource.Error) {
                    _error.value = result.message
                }
            }
            _isLoading.value = false
        }
    }

    private fun loadComments(postId: String) {
        viewModelScope.launch {
            repository.getCachedComments(postId).collect { cached ->
                if (cached.isNotEmpty()) {
                    _comments.value = cached
                }
            }
        }

        viewModelScope.launch {
            val result = repository.getComments(postId)
            if (result is Resource.Success) {
                val commentsList = result.data?.map { item ->
                    Comment(
                        id = item.id ?: DATA.EMPTY,
                        name = item.author?.displayName ?: DATA.EMPTY,
                        profileImage = "https://www.blogger.com/img/blogger-logotype-color-black-caps.png",
                        published = item.published ?: DATA.EMPTY,
                        comment = item.content ?: DATA.EMPTY
                    )
                } ?: emptyList()
                _comments.value = commentsList
                repository.insertComments(postId, commentsList)
            } else if (result is Resource.Error) {
                _error.value = result.message
            }
            _isLoading.value = false
        }
    }
}