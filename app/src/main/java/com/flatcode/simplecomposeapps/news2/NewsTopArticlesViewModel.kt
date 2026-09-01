package com.flatcode.simplecomposeapps.news2

import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.news2.base.BaseViewModel
import com.flatcode.simplecomposeapps.news2.common.Resource
import com.flatcode.simplecomposeapps.news2.data.repositories.EverythingRepository
import com.flatcode.simplecomposeapps.news2.models.NewsResponse
import com.flatcode.simplecomposeapps.news2.models.TopArticlesNewsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsTopArticlesViewModel @Inject constructor(
    private val repository: EverythingRepository
) : BaseViewModel() {

    private val _topArticles = MutableStateFlow<Resource<NewsResponse<TopArticlesNewsItem>>>(Resource.Loading())
    val topArticles: StateFlow<Resource<NewsResponse<TopArticlesNewsItem>>> = _topArticles.asStateFlow()

    fun getTopArticles(country: String) {
        viewModelScope.launch {
            repository.getTopArticles(country).collect {
                _topArticles.value = it
            }
        }
    }
}