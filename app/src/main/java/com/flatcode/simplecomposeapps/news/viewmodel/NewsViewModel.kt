package com.flatcode.simplecomposeapps.news.viewmodel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.flatcode.simplecomposeapps.news.OnFetchDataListener
import com.flatcode.simplecomposeapps.news.RequestManager
import com.flatcode.simplecomposeapps.news.model.NewsApiResponse
import com.flatcode.simplecomposeapps.news.model.NewsHeadlines

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    val headlines: List<NewsHeadlines>
        field = mutableStateListOf<NewsHeadlines>()

    val isLoading: State<Boolean>
        field = mutableStateOf(false)

    val selectedCategory: State<String>
        field = mutableStateOf("general")

    private val requestManager = RequestManager(application)

    private val listener = object : OnFetchDataListener<NewsApiResponse> {
        override fun onFetchData(list: List<NewsHeadlines?>?, message: String?) {
            isLoading.value = false
            headlines.clear()
            list?.filterNotNull()?.let { headlines.addAll(it) }
        }

        override fun onError(message: String?) {
            isLoading.value = false
        }
    }

    init {
        loadNews("general")
    }

    fun loadNews(category: String, query: String? = null) {
        isLoading.value = true
        selectedCategory.value = category
        requestManager.getNewsHeadlines(listener, category, query)
    }

    fun searchNews(query: String) {
        loadNews(selectedCategory.value, query)
    }
}
