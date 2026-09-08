package com.flatcode.simplecomposeapps.blogger.viewmodel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.flatcode.simplecomposeapps.blogger.model.Comment
import com.flatcode.simplecomposeapps.blogger.model.Label
import com.flatcode.simplecomposeapps.blogger.model.Page
import com.flatcode.simplecomposeapps.blogger.model.Post
import com.flatcode.simplecomposeapps.utils.DATA
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class BloggerViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

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
        val url = if (isSearch) {
            when (nextPageToken.value) {
                DATA.EMPTY -> "${DATA.BLOGGER_BASE_URL}${DATA.BLOG_ID}/${DATA.POSTS}/${DATA.SEARCH}?${DATA.Q}=$currentQuery&${DATA.KEY}=${DATA.BLOGGER_API}"
                else -> "${DATA.BLOGGER_BASE_URL}${DATA.BLOG_ID}/${DATA.POSTS}/${DATA.SEARCH}?${DATA.Q}=$currentQuery&${DATA.PAGE_TOKEN}=${nextPageToken.value}&${DATA.KEY}=${DATA.BLOGGER_API}"
            }
        } else {
            when (nextPageToken.value) {
                DATA.EMPTY -> "${DATA.BLOGGER_BASE_URL}${DATA.BLOG_ID}/${DATA.POSTS}?${DATA.MAX_RESULTS}=${DATA.MAX_POST_RESULTS}&${DATA.KEY}=${DATA.BLOGGER_API}"
                else -> "${DATA.BLOGGER_BASE_URL}${DATA.BLOG_ID}/${DATA.POSTS}?${DATA.MAX_RESULTS}=${DATA.MAX_POST_RESULTS}&${DATA.PAGE_TOKEN}=${nextPageToken.value}&${DATA.KEY}=${DATA.BLOGGER_API}"
            }
        }

        val stringRequest = StringRequest(Request.Method.GET, url, { response ->
            _isLoading.value = false
            if (response.isNullOrEmpty()) return@StringRequest
            try {
                val jsonObject = JSONObject(response)
                nextPageToken.value = jsonObject.optString(DATA.NEXT_PAGE_TOKEN, "end")

                val jsonArray = jsonObject.optJSONArray(DATA.ITEMS)
                if (jsonArray != null) {
                    for (i in 0 until jsonArray.length()) {
                        val item = jsonArray.getJSONObject(i)
                        posts.add(parsePost(item))
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, {
            _isLoading.value = false
        })

        Volley.newRequestQueue(getApplication()).add(stringRequest)
    }

    fun loadPages() {
        _isLoading.value = true
        pages.clear()
        val url =
            "${DATA.BLOGGER_BASE_URL}${DATA.BLOG_ID}/${DATA.PAGES}?${DATA.KEY}=${DATA.BLOGGER_API}"

        val stringRequest = StringRequest(Request.Method.GET, url, { response ->
            _isLoading.value = false
            if (response.isNullOrEmpty()) return@StringRequest
            try {
                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.optJSONArray(DATA.ITEMS)
                if (jsonArray != null) {
                    for (i in 0 until jsonArray.length()) {
                        val item = jsonArray.getJSONObject(i)
                        pages.add(parsePage(item))
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, {
            _isLoading.value = false
        })

        Volley.newRequestQueue(getApplication()).add(stringRequest)
    }

    fun loadPostDetails(postId: String) {
        _isLoading.value = true
        _details.value = null
        labels.clear()
        comments.clear()

        val url =
            "${DATA.BLOGGER_BASE_URL}${DATA.BLOG_ID}/${DATA.POSTS}/$postId?${DATA.KEY}=${DATA.BLOGGER_API}"

        val stringRequest = StringRequest(Request.Method.GET, url, { response ->
            if (response.isNullOrEmpty()) {
                _isLoading.value = false
                return@StringRequest
            }
            try {
                val jsonObject = JSONObject(response)
                _details.value = parsePost(jsonObject)

                val labelsArray = jsonObject.optJSONArray(DATA.LABELS)
                if (labelsArray != null) {
                    for (i in 0 until labelsArray.length()) {
                        labels.add(Label(labelsArray.getString(i)))
                    }
                }
                loadComments(postId)
            } catch (e: Exception) {
                e.printStackTrace()
                _isLoading.value = false
            }
        }, {
            _isLoading.value = false
        })

        Volley.newRequestQueue(getApplication()).add(stringRequest)
    }

    fun loadPageDetails(pageId: String) {
        _isLoading.value = true
        _details.value = null
        labels.clear()
        comments.clear()

        val url =
            "${DATA.BLOGGER_BASE_URL}${DATA.BLOG_ID}/${DATA.PAGES}/$pageId?${DATA.KEY}=${DATA.BLOGGER_API}"

        val stringRequest = StringRequest(Request.Method.GET, url, { response ->
            _isLoading.value = false
            if (response.isNullOrEmpty()) return@StringRequest
            try {
                val jsonObject = JSONObject(response)
                _details.value = parsePost(jsonObject)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, {
            _isLoading.value = false
        })

        Volley.newRequestQueue(getApplication()).add(stringRequest)
    }

    private fun loadComments(postId: String) {
        val url =
            "${DATA.BLOGGER_BASE_URL}${DATA.BLOG_ID}/${DATA.POSTS}/$postId/${DATA.COMMENTS_KEY}?${DATA.KEY}=${DATA.BLOGGER_API}"

        val stringRequest = StringRequest(Request.Method.GET, url, { response ->
            _isLoading.value = false
            if (response.isNullOrEmpty()) return@StringRequest
            try {
                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.optJSONArray(DATA.ITEMS)
                if (jsonArray != null) {
                    for (i in 0 until jsonArray.length()) {
                        val item = jsonArray.getJSONObject(i)
                        val author = item.getJSONObject(DATA.AUTHOR)
                        val image = author.getJSONObject(DATA.IMAGE).getString(DATA.URL)
                        comments.add(
                            Comment(
                                id = item.getString(DATA.ID),
                                name = author.getString(DATA.DISPLAY_NAME),
                                profileImage = "https:$image",
                                published = item.getString(DATA.PUBLISHED),
                                comment = item.getString(DATA.CONTENT)
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, {
            _isLoading.value = false
        })

        Volley.newRequestQueue(getApplication()).add(stringRequest)
    }

    private fun parsePost(item: JSONObject) = Post(
        authorName = item.getJSONObject(DATA.AUTHOR).getString(DATA.DISPLAY_NAME),
        content = item.optString(DATA.CONTENT),
        id = item.getString(DATA.ID),
        published = item.getString(DATA.PUBLISHED),
        selfLink = item.optString(DATA.SELF_LINK),
        title = item.getString(DATA.TITLE),
        updated = item.optString(DATA.UPDATED),
        url = item.optString(DATA.URL)
    )

    private fun parsePage(item: JSONObject) = Page(
        authorName = item.getJSONObject(DATA.AUTHOR).getString(DATA.DISPLAY_NAME),
        content = item.optString(DATA.CONTENT),
        id = item.getString(DATA.ID),
        published = item.getString(DATA.PUBLISHED),
        selfLink = item.optString(DATA.SELF_LINK),
        title = item.getString(DATA.TITLE),
        updated = item.optString(DATA.UPDATED),
        url = item.optString(DATA.URL)
    )
}