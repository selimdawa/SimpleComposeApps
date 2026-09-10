package com.flatcode.simplecomposeapps.news.data.repository

import com.flatcode.simplecomposeapps.news.data.local.NewsDao
import com.flatcode.simplecomposeapps.news.service.NewsAPI
import com.flatcode.simplecomposeapps.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val api: NewsAPI, private val newsDao: NewsDao
) {
    suspend fun getNewsHeadlines(category: String?, query: String?) = withContext(Dispatchers.IO) {
        try {
            val response = api.getNewsHeadlines(category, query)
            val articles = response.articles ?: emptyList()

            // Update cache if it's a category fetch (not a search query)
            if (category != null && query == null) {
                articles.forEach { it.category = category }
                newsDao.deleteByCategory(category)
                newsDao.insertNews(articles)
            }

            Resource.Success(articles)
        } catch (e: Exception) {
            if (category != null && query == null) {
                val cachedNews = newsDao.getNewsByCategory(category)
                if (cachedNews.isNotEmpty()) {
                    Resource.Success(cachedNews)
                } else {
                    Resource.Error(e.message ?: "An error occurred and no cached data found")
                }
            } else {
                Resource.Error(e.message ?: "An error occurred")
            }
        }
    }
}