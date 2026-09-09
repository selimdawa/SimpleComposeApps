package com.flatcode.simplecomposeapps.news.data.repository

import com.flatcode.simplecomposeapps.news.service.NewsAPI
import com.flatcode.simplecomposeapps.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val api: NewsAPI
) {
    suspend fun getNewsHeadlines(category: String?, query: String?) = withContext(Dispatchers.IO) {
        try {
            val response = api.getNewsHeadlines(category, query)
            Resource.Success(response.articles ?: emptyList())
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }
}