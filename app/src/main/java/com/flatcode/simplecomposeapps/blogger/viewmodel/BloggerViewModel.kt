package com.flatcode.simplecomposeapps.blogger.viewmodel

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BloggerViewModel @Inject constructor(
    private val repository: BloggerRepository
) : ViewModel() {

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()

    private var allPosts = listOf<Post>()

    private val _pages = MutableStateFlow<List<Page>>(emptyList())
    val pages: StateFlow<List<Page>> = _pages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _nextPageToken = MutableStateFlow(DATA.EMPTY)
    val hasMore: Boolean get() = _nextPageToken.value != "end"

    private val _details = MutableStateFlow<Post?>(null)
    val details: StateFlow<Post?> = _details.asStateFlow()

    private val _labels = MutableStateFlow<List<Label>>(emptyList())
    val labels: StateFlow<List<Label>> = _labels.asStateFlow()

    private val _comments = MutableStateFlow<List<Comment>>(emptyList())
    val comments: StateFlow<List<Comment>> = _comments.asStateFlow()

    private var currentQuery = DATA.EMPTY

    private var detailsJob: Job? = null

    init {
        observeCachedData()
    }

    private fun observeCachedData() {
        Timber.d("Observing cached data for posts and pages")
        viewModelScope.launch {
            repository.getCachedPosts().collect { list ->
                if (list.isNotEmpty() || _posts.value.isEmpty()) {
                    _posts.value = list
                    allPosts = list
                }
            }
        }
        viewModelScope.launch {
            repository.getCachedPages().collect { list ->
                if (list.isNotEmpty() || _pages.value.isEmpty()) {
                    _pages.value = list
                }
            }
        }
    }

    fun loadPosts(isLoadMore: Boolean = false) {
        Timber.d("Loading posts, isLoadMore: %b", isLoadMore)
        if (isLoadMore && _nextPageToken.value == "end") return
        if (!isLoadMore) {
            _nextPageToken.value = "1"
            currentQuery = DATA.EMPTY
        }

        fetchPosts(isSearch = false)
    }

    fun searchPosts(query: String, isLoadMore: Boolean = false) {
        Timber.d("Searching posts, query: %s, isLoadMore: %b", query, isLoadMore)
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
        Timber.d("Filtering posts, query: %s", query)
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
                    startIndex = if (_nextPageToken.value == DATA.EMPTY) "1" else _nextPageToken.value
                )
            } else {
                repository.getPosts(
                    startIndex = if (_nextPageToken.value == DATA.EMPTY) "1" else _nextPageToken.value
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
                    Timber.d("Fetched %d posts successfully", newList.size)
                }

                is Resource.Error -> {
                    _error.value = result.message
                    Timber.e("Error fetching posts: %s", result.message)
                }

                else -> {}
            }
            _isLoading.value = false
        }
    }

    fun loadPages() {
        Timber.d("Loading pages")
        _isLoading.value = true
        _error.value = null
        viewModelScope.launch {
            val result = repository.getPages()
            if (result is Resource.Success) {
                val data = result.data ?: emptyList()
                repository.insertPages(data)
                Timber.d("Loaded %d pages successfully", data.size)
            } else if (result is Resource.Error) {
                _error.value = result.message
                Timber.e("Error loading pages: %s", result.message)
            }
            _isLoading.value = false
        }
    }

    fun loadPostDetails(postId: String) {
        Timber.d("Loading post details, postId: %s", postId)
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
                Timber.d("Loaded post details successfully")
            } else {
                if (result is Resource.Error) {
                    _error.value = result.message
                    Timber.e("Error loading post details: %s", result.message)
                }
                _isLoading.value = false
            }
        }
    }

    fun loadPageDetails(pageId: String) {
        Timber.d("Loading page details, pageId: %s", pageId)
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
                Timber.d("Loaded page details successfully")
            } else {
                if (result is Resource.Error) {
                    _error.value = result.message
                    Timber.e("Error loading page details: %s", result.message)
                }
            }
            _isLoading.value = false
        }
    }

    private fun loadComments(postId: String) {
        Timber.d("Loading comments for postId: %s", postId)
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
                Timber.d("Loaded %d comments successfully", commentsList.size)
            } else if (result is Resource.Error) {
                _error.value = result.message
                Timber.e("Error loading comments: %s", result.message)
            }
            _isLoading.value = false
        }
    }
}