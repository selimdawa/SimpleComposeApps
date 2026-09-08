package com.flatcode.simplecomposeapps.news.service

import com.flatcode.simplecomposeapps.news.model.NewsApiResponse
import com.flatcode.simplecomposeapps.utils.DATA
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsAPI @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getNewsHeadlines(
        category: String?,
        query: String?
    ): NewsApiResponse {
        return client.get("https://newsapi.org/v2/top-headlines") {
            parameter("country", "us")
            parameter("category", category)
            parameter("q", query)
            parameter("apiKey", DATA.NEWS_API)
        }.body()
    }
}