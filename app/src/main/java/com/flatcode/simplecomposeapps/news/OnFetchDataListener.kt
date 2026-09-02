package com.flatcode.simplecomposeapps.news

import com.flatcode.simplecomposeapps.news.model.NewsHeadlines

interface OnFetchDataListener<NewsApiResponse> {
    fun onFetchData(list: List<NewsHeadlines?>?, message: String?)
    fun onError(message: String?)
}