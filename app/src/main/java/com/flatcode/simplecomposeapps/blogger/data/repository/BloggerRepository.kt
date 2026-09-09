package com.flatcode.simplecomposeapps.blogger.data.repository

import com.flatcode.simplecomposeapps.blogger.data.network.BloggerApi
import com.flatcode.simplecomposeapps.blogger.model.Post
import com.flatcode.simplecomposeapps.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BloggerRepository @Inject constructor(
    private val api: BloggerApi
) {
    suspend fun getPosts(pageToken: String? = null) = withContext(Dispatchers.IO) {
        try {
            val response = api.getPosts(pageToken = pageToken)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun searchPosts(query: String, pageToken: String? = null) = withContext(Dispatchers.IO) {
        try {
            val response = api.searchPosts(query = query, pageToken = pageToken)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun getPages() = withContext(Dispatchers.IO) {
        try {
            val response = api.getPages()
            Resource.Success(response.items ?: emptyList())
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun getPostDetails(postId: String) = withContext(Dispatchers.IO) {
        try {
            val response = api.getPostDetails(postId)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun getPageDetails(pageId: String) = withContext(Dispatchers.IO) {
        try {
            val response = api.getPageDetails(pageId)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun getComments(postId: String) = withContext(Dispatchers.IO) {
        try {
            val response = api.getComments(postId)
            Resource.Success(response.items ?: emptyList())
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }
}