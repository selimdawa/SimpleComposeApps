package com.flatcode.simplecomposeapps.news2.data.remote

import com.flatcode.simplecomposeapps.news2.models.NewsResponse
import com.flatcode.simplecomposeapps.news2.models.EverythingNewsItem
import com.flatcode.simplecomposeapps.news2.models.TopArticlesNewsItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiServices {

    @GET("everything")
    suspend fun getEverything(
        @Query("q") query: String
    ): Response<NewsResponse<EverythingNewsItem>>

    @GET("top-headlines")
    suspend fun getTopArticles(
        @Query("country") country: String
    ): Response<NewsResponse<TopArticlesNewsItem>>
}