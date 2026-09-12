package com.flatcode.simplecomposeapps.blogger.data.network

import com.flatcode.simplecomposeapps.utils.DATA
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BloggerApi @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getPosts(
        url: String = DATA.FEED_URL,
        startIndex: String = "1",
        maxResults: String = DATA.MAX_POST_RESULTS
    ): String {
        return client.get(url) {
            parameter("start-index", startIndex)
            parameter("max-results", maxResults)
        }.body()
    }

    suspend fun searchPosts(
        query: String,
        startIndex: String = "1",
        maxResults: String = DATA.MAX_POST_RESULTS
    ): String {
        return client.get(DATA.FEED_URL) {
            parameter("q", query)
            parameter("start-index", startIndex)
            parameter("max-results", maxResults)
        }.body()
    }

    suspend fun getPages(): String {
        return client.get(DATA.PAGES_FEED_URL).body()
    }

    suspend fun getPostDetails(postId: String): String {
        val url = "https://www.blogger.com/feeds/${DATA.BLOG_ID}/posts/default/$postId"
        return client.get(url).body()
    }

    suspend fun getPageDetails(pageId: String): String {
        val url = "https://www.blogger.com/feeds/${DATA.BLOG_ID}/pages/default/$pageId"
        return client.get(url).body()
    }

    suspend fun getComments(postId: String): String {
        val url = "https://www.blogger.com/feeds/${DATA.BLOG_ID}/$postId/comments/default"
        return client.get(url).body()
    }
}