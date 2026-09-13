package com.flatcode.simplecomposeapps.news2.viewmodel

import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.news2.base.BaseViewModel
import com.flatcode.simplecomposeapps.news2.data.repositories.EverythingRepository
import com.flatcode.simplecomposeapps.news2.models.NewsResponse
import com.flatcode.simplecomposeapps.news2.models.TopArticlesNewsItem
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NewsTopArticlesViewModel @Inject constructor(
    private val repository: EverythingRepository
) : BaseViewModel() {

    private val _topArticles = MutableStateFlow<Resource<NewsResponse<TopArticlesNewsItem>>>(Resource.Loading())
    val topArticles: StateFlow<Resource<NewsResponse<TopArticlesNewsItem>>> = _topArticles.asStateFlow()

    fun getTopArticles(country: String) {
        Timber.d("Fetching top news articles for country: %s", country)
        viewModelScope.launch {
            repository.getTopArticles(country).collect {
                _topArticles.value = it
            }
        }
    }
}