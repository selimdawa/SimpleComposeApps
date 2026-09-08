package com.flatcode.simplecomposeapps.news2.data.remote

import com.flatcode.simplecomposeapps.news2.models.EverythingNewsItem
import com.flatcode.simplecomposeapps.news2.models.NewsResponse
import com.flatcode.simplecomposeapps.news2.models.TopArticlesNewsItem
import com.flatcode.simplecomposeapps.utils.DATA
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsApiServices @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getEverything(
        query: String
    ): NewsResponse<EverythingNewsItem> {
        return client.get("${DATA.BASE_URL_NEWS}everything") {
            parameter("q", query)
            parameter("apiKey", DATA.API_NEWS)
        }.body()
    }

    suspend fun getTopArticles(
        country: String
    ): NewsResponse<TopArticlesNewsItem> {
        return client.get("${DATA.BASE_URL_NEWS}top-headlines") {
            parameter("country", country)
            parameter("apiKey", DATA.API_NEWS)
        }.body()
    }
}
