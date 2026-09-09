package com.flatcode.simplecomposeapps.news2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.news2.base.BaseViewModel
import com.flatcode.simplecomposeapps.news2.data.repositories.EverythingRepository
import com.flatcode.simplecomposeapps.news2.models.EverythingNewsItem
import com.flatcode.simplecomposeapps.news2.models.NewsResponse
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsEverythingViewModel @Inject constructor(
    private val repository: EverythingRepository
) : BaseViewModel() {

    private val _everything = MutableLiveData<Resource<NewsResponse<EverythingNewsItem>>>(Resource.Loading())
    val everything: LiveData<Resource<NewsResponse<EverythingNewsItem>>> = _everything

    fun getEverything(query: String) {
        viewModelScope.launch {
            repository.getEverything(query).collect {
                _everything.value = it
            }
        }
    }
}