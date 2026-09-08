package com.flatcode.simplecomposeapps.news2.viewmodel

import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.news2.base.BaseViewModel
import com.flatcode.simplecomposeapps.news2.data.repositories.EverythingRepository
import com.flatcode.simplecomposeapps.news2.models.EverythingNewsItem
import com.flatcode.simplecomposeapps.news2.models.NewsResponse
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsEverythingViewModel @Inject constructor(
    private val repository: EverythingRepository
) : BaseViewModel() {

    val everything = MutableStateFlow<Resource<NewsResponse<EverythingNewsItem>>>(Resource.Loading())

    fun getEverything(query: String) {
        viewModelScope.launch {
            repository.getEverything(query).collect {
                everything.value = it
            }
        }
    }
}