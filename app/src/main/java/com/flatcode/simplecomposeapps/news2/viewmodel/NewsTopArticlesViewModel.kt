package com.flatcode.simplecomposeapps.news2.viewmodel

import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.news2.base.BaseViewModel
import com.flatcode.simplecomposeapps.news2.data.repositories.EverythingRepository
import com.flatcode.simplecomposeapps.news2.models.NewsResponse
import com.flatcode.simplecomposeapps.news2.models.TopArticlesNewsItem
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsTopArticlesViewModel @Inject constructor(
    private val repository: EverythingRepository
) : BaseViewModel() {

    val topArticles = MutableStateFlow<Resource<NewsResponse<TopArticlesNewsItem>>>(Resource.Loading())

    fun getTopArticles(country: String) {
        viewModelScope.launch {
            repository.getTopArticles(country).collect {
                topArticles.value = it
            }
        }
    }
}