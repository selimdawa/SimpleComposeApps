package com.flatcode.simplecomposeapps.news.viewmodel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.news.OnFetchDataListener
import com.flatcode.simplecomposeapps.news.RequestManager
import com.flatcode.simplecomposeapps.news.data.NewsDao
import com.flatcode.simplecomposeapps.news.data.NewsEntity
import com.flatcode.simplecomposeapps.news.model.NewsApiResponse
import com.flatcode.simplecomposeapps.news.model.NewsHeadlines
import com.flatcode.simplecomposeapps.news.model.Source
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    application: Application,
    private val newsDao: NewsDao,
    private val networkHelper: NetworkHelper
) : AndroidViewModel(application) {

    val headlines: List<NewsHeadlines>
        field = mutableStateListOf<NewsHeadlines>()

    val isLoading: State<Boolean>
        field = mutableStateOf(true)

    val selectedCategory: State<String>
        field = mutableStateOf("general")

    val selectedHeadline: State<NewsHeadlines?>
        field = mutableStateOf<NewsHeadlines?>(null)

    val savedHeadlines = mutableStateListOf<NewsHeadlines>()

    val errorMessage = mutableStateOf<String?>(null)

    private val requestManager = RequestManager(application)

    private val listener = object : OnFetchDataListener<NewsApiResponse> {
        override fun onFetchData(list: List<NewsHeadlines?>?, message: String?) {
            val news = list?.filterNotNull() ?: emptyList()
            val category = selectedCategory.value
            
            viewModelScope.launch {
                newsDao.deleteCacheByCategory(category)
                newsDao.insertAll(news.map { mapToEntity(it).copy(isCache = true, category = category) })
                
                headlines.clear()
                headlines.addAll(news)
                isLoading.value = false
            }
        }

        override fun onError(message: String?) {
            errorMessage.value = if (message?.contains("UnknownHostException") == true || message == "Error") 
                "No Internet Connection" else (message ?: "Unknown error")
            loadFromCache(selectedCategory.value, false)
        }
    }

    init {
        loadNews("general")
        observeSavedNews()
    }

    private fun observeSavedNews() {
        viewModelScope.launch {
            newsDao.getAllNews().collectLatest { entities ->
                savedHeadlines.clear()
                savedHeadlines.addAll(entities.map { mapFromEntity(it) })
            }
        }
    }

    private fun mapToEntity(headline: NewsHeadlines): NewsEntity {
        return NewsEntity(
            title = headline.title,
            author = headline.author,
            description = headline.description,
            url = headline.url,
            urlToImage = headline.urlToImage,
            publishedAt = headline.publishedAt,
            content = headline.content,
            sourceName = headline.source?.name ?: ""
        )
    }

    private fun mapFromEntity(entity: NewsEntity): NewsHeadlines {
        return NewsHeadlines(
            source = Source(name = entity.sourceName),
            author = entity.author,
            title = entity.title,
            description = entity.description,
            url = entity.url,
            urlToImage = entity.urlToImage,
            publishedAt = entity.publishedAt,
            content = entity.content
        )
    }

    fun loadNews(category: String, query: String? = null) {
        isLoading.value = true
        errorMessage.value = null
        selectedCategory.value = category

        if (!networkHelper.isNetworkConnected()) {
            loadFromCache(category)
            return
        }

        requestManager.getNewsHeadlines(listener, category, query)
    }

    private fun loadFromCache(category: String, updateLoading: Boolean = true) {
        viewModelScope.launch {
            val cache = newsDao.getCacheByCategory(category)
            if (cache.isNotEmpty()) {
                headlines.clear()
                headlines.addAll(cache.map { mapFromEntity(it) })
            } else {
                errorMessage.value = Strings.NO_INFORMATION
            }
            if (updateLoading) {
                isLoading.value = false
            }
        }
    }

    fun retry() {
        loadNews(selectedCategory.value)
    }

    fun searchNews(query: String) {
        loadNews(selectedCategory.value, query)
    }
}
