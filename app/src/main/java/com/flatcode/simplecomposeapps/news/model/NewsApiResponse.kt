package com.flatcode.simplecomposeapps.news.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsApiResponse(
    var status: String? = null,
    var totalResults: Int = 0,
    var articles: List<NewsHeadlines>? = null
)