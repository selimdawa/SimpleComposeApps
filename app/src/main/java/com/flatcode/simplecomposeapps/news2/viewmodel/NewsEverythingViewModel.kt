package com.flatcode.simplecomposeapps.news2.viewmodel

import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.news2.base.BaseViewModel
import com.flatcode.simplecomposeapps.news2.data.repositories.EverythingRepository
import com.flatcode.simplecomposeapps.news2.models.EverythingNewsItem
import com.flatcode.simplecomposeapps.news2.models.NewsResponse
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NewsEverythingViewModel @Inject constructor(
    private val repository: EverythingRepository
) : BaseViewModel() {

    private val _everything = MutableStateFlow<Resource<NewsResponse<EverythingNewsItem>>>(Resource.Loading())
    val everything: StateFlow<Resource<NewsResponse<EverythingNewsItem>>> = _everything.asStateFlow()

    fun getEverything(query: String) {
        Timber.d("Fetching everything news for query: %s", query)
        viewModelScope.launch {
            repository.getEverything(query).collect {
                _everything.value = it
            }
        }
    }
}