package com.flatcode.simplecomposeapps.blogger.data.network

import com.flatcode.simplecomposeapps.blogger.model.BloggerResponse
import com.flatcode.simplecomposeapps.blogger.model.CommentResponse
import com.flatcode.simplecomposeapps.blogger.model.Page
import com.flatcode.simplecomposeapps.blogger.model.Post
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
        blogId: String = DATA.BLOG_ID,
        maxResults: String = DATA.MAX_POST_RESULTS,
        pageToken: String? = null,
        apiKey: String = DATA.BLOGGER_API
    ): BloggerResponse<Post> {
        return client.get("${DATA.BLOGGER_BASE_URL}$blogId/${DATA.POSTS}") {
            parameter(DATA.MAX_RESULTS, maxResults)
            parameter(DATA.KEY, apiKey)
            pageToken?.let { parameter(DATA.PAGE_TOKEN, it) }
        }.body()
    }

    suspend fun searchPosts(
        query: String,
        blogId: String = DATA.BLOG_ID,
        pageToken: String? = null,
        apiKey: String = DATA.BLOGGER_API
    ): BloggerResponse<Post> {
        return client.get("${DATA.BLOGGER_BASE_URL}$blogId/${DATA.POSTS}/${DATA.SEARCH}") {
            parameter(DATA.Q, query)
            parameter(DATA.KEY, apiKey)
            pageToken?.let { parameter(DATA.PAGE_TOKEN, it) }
        }.body()
    }

    suspend fun getPages(
        blogId: String = DATA.BLOG_ID,
        apiKey: String = DATA.BLOGGER_API
    ): BloggerResponse<Page> {
        return client.get("${DATA.BLOGGER_BASE_URL}$blogId/${DATA.PAGES}") {
            parameter(DATA.KEY, apiKey)
        }.body()
    }

    suspend fun getPostDetails(
        postId: String,
        blogId: String = DATA.BLOG_ID,
        apiKey: String = DATA.BLOGGER_API
    ): Post {
        return client.get("${DATA.BLOGGER_BASE_URL}$blogId/${DATA.POSTS}/$postId") {
            parameter(DATA.KEY, apiKey)
        }.body()
    }

    suspend fun getPageDetails(
        pageId: String,
        blogId: String = DATA.BLOG_ID,
        apiKey: String = DATA.BLOGGER_API
    ): Page {
        return client.get("${DATA.BLOGGER_BASE_URL}$blogId/${DATA.PAGES}/$pageId") {
            parameter(DATA.KEY, apiKey)
        }.body()
    }

    suspend fun getComments(
        postId: String,
        blogId: String = DATA.BLOG_ID,
        apiKey: String = DATA.BLOGGER_API
    ): CommentResponse {
        return client.get("${DATA.BLOGGER_BASE_URL}$blogId/${DATA.POSTS}/$postId/${DATA.COMMENTS_KEY}") {
            parameter(DATA.KEY, apiKey)
        }.body()
    }
}