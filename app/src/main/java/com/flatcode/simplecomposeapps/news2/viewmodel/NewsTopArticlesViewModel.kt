package com.flatcode.simplecomposeapps.news2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.news2.base.BaseViewModel
import com.flatcode.simplecomposeapps.news2.data.repositories.EverythingRepository
import com.flatcode.simplecomposeapps.news2.models.NewsResponse
import com.flatcode.simplecomposeapps.news2.models.TopArticlesNewsItem
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsTopArticlesViewModel @Inject constructor(
    private val repository: EverythingRepository
) : BaseViewModel() {

    private val _topArticles = MutableLiveData<Resource<NewsResponse<TopArticlesNewsItem>>>(Resource.Loading())
    val topArticles: LiveData<Resource<NewsResponse<TopArticlesNewsItem>>> = _topArticles

    fun getTopArticles(country: String) {
        viewModelScope.launch {
            repository.getTopArticles(country).collect {
                _topArticles.value = it
            }
        }
    }
}