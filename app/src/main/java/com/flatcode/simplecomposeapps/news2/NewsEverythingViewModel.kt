package com.flatcode.simplecomposeapps.news2

import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.news2.base.BaseViewModel
import com.flatcode.simplecomposeapps.news2.common.Resource
import com.flatcode.simplecomposeapps.news2.data.repositories.EverythingRepository
import com.flatcode.simplecomposeapps.news2.models.EverythingNewsItem
import com.flatcode.simplecomposeapps.news2.models.NewsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsEverythingViewModel @Inject constructor(
    private val repository: EverythingRepository
) : BaseViewModel() {

    private val _everything = MutableStateFlow<Resource<NewsResponse<EverythingNewsItem>>>(Resource.Loading())
    val everything: StateFlow<Resource<NewsResponse<EverythingNewsItem>>> = _everything.asStateFlow()

    fun getEverything(query: String) {
        viewModelScope.launch {
            repository.getEverything(query).collect {
                _everything.value = it
            }
        }
    }
}