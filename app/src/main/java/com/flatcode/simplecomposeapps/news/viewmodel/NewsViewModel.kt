package com.flatcode.simplecomposeapps.news.viewmodel

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.news.model.NewsHeadlines
import com.flatcode.simplecomposeapps.news.service.NewsAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    application: Application, private val newsApi: NewsAPI
) : AndroidViewModel(application) {

    val headlines: List<NewsHeadlines>
        field = mutableStateListOf<NewsHeadlines>()

    val isLoading: MutableState<Boolean>
        field = mutableStateOf(false)

    val selectedCategory: MutableState<String>
        field = mutableStateOf("general")

    val selectedHeadline: MutableState<NewsHeadlines?>
        field = mutableStateOf<NewsHeadlines?>(null)

    init {
        loadNews("general")
    }

    fun loadNews(category: String, query: String? = null) {
        isLoading.value = true
        selectedCategory.value = category
        viewModelScope.launch {
            try {
                val response = newsApi.getNewsHeadlines(category, query)
                isLoading.value = false
                headlines.clear()
                response.articles?.filterNotNull()?.let { headlines.addAll(it) }
            } catch (e: Exception) {
                isLoading.value = false
                e.printStackTrace()
            }
        }
    }

    fun searchNews(query: String) {
        loadNews(selectedCategory.value, query)
    }
}
