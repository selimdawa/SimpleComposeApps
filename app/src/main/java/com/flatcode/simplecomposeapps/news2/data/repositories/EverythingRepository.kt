package com.flatcode.simplecomposeapps.news2.data.repositories

import com.flatcode.simplecomposeapps.news2.base.BaseRepository
import com.flatcode.simplecomposeapps.news2.data.remote.NewsApiServices
import javax.inject.Inject

class EverythingRepository @Inject constructor(
    private val api: NewsApiServices
) : BaseRepository() {

    fun getEverything(query: String) = doRequest {
        api.getEverything(query)
    }

    fun getTopArticles(country: String) = doRequest {
        api.getTopArticles(country)
    }
}