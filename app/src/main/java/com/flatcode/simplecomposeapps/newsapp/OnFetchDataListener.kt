package com.flatcode.simplecomposeapps.newsapp

import com.flatcode.simplecomposeapps.newsapp.model.NewsHeadlines

interface OnFetchDataListener<NewsApiResponse> {
    fun onFetchData(list: List<NewsHeadlines?>?, message: String?)
    fun onError(message: String?)
}