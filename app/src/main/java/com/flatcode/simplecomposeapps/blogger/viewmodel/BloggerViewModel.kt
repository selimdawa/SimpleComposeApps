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
import org.json.JSONObject

class BloggerViewModel(application: Application) : AndroidViewModel(application) {

    val posts: List<Post>
        field = mutableStateListOf<Post>()

    val pages: List<Page>
        field = mutableStateListOf<Page>()

    val isLoading: State<Boolean>
        field = mutableStateOf(false)

    private val nextPageToken = mutableStateOf(DATA.EMPTY)

    val hasMore: Boolean get() = nextPageToken.value != "end"

    val details: State<Post?>
        field = mutableStateOf<Post?>(null)

    val labels: List<Label>
        field = mutableStateListOf<Label>()

    val comments: List<Comment>
        field = mutableStateListOf<Comment>()

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
        isLoading.value = true
        val url = if (isSearch) {
            when (nextPageToken.value) {
                DATA.EMPTY -> "https://www.googleapis.com/blogger/v3/blogs/${DATA.BLOG_ID}/posts/search?q=$currentQuery&key=${DATA.BLOGGER_API}"
                else -> "https://www.googleapis.com/blogger/v3/blogs/${DATA.BLOG_ID}/posts/search?q=$currentQuery&pageToken=${nextPageToken.value}&key=${DATA.BLOGGER_API}"
            }
        } else {
            when (nextPageToken.value) {
                DATA.EMPTY -> "https://www.googleapis.com/blogger/v3/blogs/${DATA.BLOG_ID}/posts?maxResults=${DATA.MAX_POST_RESULTS}&key=${DATA.BLOGGER_API}"
                else -> "https://www.googleapis.com/blogger/v3/blogs/${DATA.BLOG_ID}/posts?maxResults=${DATA.MAX_POST_RESULTS}&pageToken=${nextPageToken.value}&key=${DATA.BLOGGER_API}"
            }
        }

        val stringRequest = StringRequest(Request.Method.GET, url, { response ->
            isLoading.value = false
            if (response.isNullOrEmpty()) return@StringRequest
            try {
                val jsonObject = JSONObject(response)
                nextPageToken.value = jsonObject.optString("nextPageToken", "end")

                val jsonArray = jsonObject.optJSONArray("items")
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
            isLoading.value = false
        })

        Volley.newRequestQueue(getApplication()).add(stringRequest)
    }

    fun loadPages() {
        isLoading.value = true
        pages.clear()
        val url =
            "https://www.googleapis.com/blogger/v3/blogs/${DATA.BLOG_ID}/pages?key=${DATA.BLOGGER_API}"

        val stringRequest = StringRequest(Request.Method.GET, url, { response ->
            isLoading.value = false
            if (response.isNullOrEmpty()) return@StringRequest
            try {
                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.optJSONArray("items")
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
            isLoading.value = false
        })

        Volley.newRequestQueue(getApplication()).add(stringRequest)
    }

    fun loadPostDetails(postId: String) {
        isLoading.value = true
        details.value = null
        labels.clear()
        comments.clear()

        val url =
            "https://www.googleapis.com/blogger/v3/blogs/${DATA.BLOG_ID}/posts/$postId?key=${DATA.BLOGGER_API}"

        val stringRequest = StringRequest(Request.Method.GET, url, { response ->
            if (response.isNullOrEmpty()) {
                isLoading.value = false
                return@StringRequest
            }
            try {
                val jsonObject = JSONObject(response)
                details.value = parsePost(jsonObject)

                val labelsArray = jsonObject.optJSONArray("labels")
                if (labelsArray != null) {
                    for (i in 0 until labelsArray.length()) {
                        labels.add(Label(labelsArray.getString(i)))
                    }
                }
                loadComments(postId)
            } catch (e: Exception) {
                e.printStackTrace()
                isLoading.value = false
            }
        }, {
            isLoading.value = false
        })

        Volley.newRequestQueue(getApplication()).add(stringRequest)
    }

    fun loadPageDetails(pageId: String) {
        isLoading.value = true
        details.value = null
        labels.clear()
        comments.clear()

        val url =
            "https://www.googleapis.com/blogger/v3/blogs/${DATA.BLOG_ID}/pages/$pageId?key=${DATA.BLOGGER_API}"

        val stringRequest = StringRequest(Request.Method.GET, url, { response ->
            isLoading.value = false
            if (response.isNullOrEmpty()) return@StringRequest
            try {
                val jsonObject = JSONObject(response)
                details.value = parsePost(jsonObject)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, {
            isLoading.value = false
        })

        Volley.newRequestQueue(getApplication()).add(stringRequest)
    }

    private fun loadComments(postId: String) {
        val url =
            "https://www.googleapis.com/blogger/v3/blogs/${DATA.BLOG_ID}/posts/$postId/comments?key=${DATA.BLOGGER_API}"

        val stringRequest = StringRequest(Request.Method.GET, url, { response ->
            isLoading.value = false
            if (response.isNullOrEmpty()) return@StringRequest
            try {
                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.optJSONArray("items")
                if (jsonArray != null) {
                    for (i in 0 until jsonArray.length()) {
                        val item = jsonArray.getJSONObject(i)
                        val author = item.getJSONObject("author")
                        val image = author.getJSONObject("image").getString("url")
                        comments.add(
                            Comment(
                                id = item.getString("id"),
                                name = author.getString("displayName"),
                                profileImage = "https:$image",
                                published = item.getString("published"),
                                comment = item.getString("content")
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, {
            isLoading.value = false
        })

        Volley.newRequestQueue(getApplication()).add(stringRequest)
    }

    private fun parsePost(item: JSONObject) = Post(
        authorName = item.getJSONObject("author").getString("displayName"),
        content = item.optString("content"),
        id = item.getString("id"),
        published = item.getString("published"),
        selfLink = item.optString("selfLink"),
        title = item.getString("title"),
        updated = item.optString("updated"),
        url = item.optString("url")
    )

    private fun parsePage(item: JSONObject) = Page(
        authorName = item.getJSONObject("author").getString("displayName"),
        content = item.optString("content"),
        id = item.getString("id"),
        published = item.getString("published"),
        selfLink = item.optString("selfLink"),
        title = item.getString("title"),
        updated = item.optString("updated"),
        url = item.optString("url")
    )
}