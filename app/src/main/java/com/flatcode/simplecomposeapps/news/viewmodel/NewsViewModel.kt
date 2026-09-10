package com.flatcode.simplecomposeapps.news.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.news.data.repository.NewsRepository
import com.flatcode.simplecomposeapps.news.model.NewsHeadlines
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    application: Application,
    private val repository: NewsRepository
) : AndroidViewModel(application) {

    private val _headlines = MutableLiveData<List<NewsHeadlines>>(emptyList())
    val headlines: LiveData<List<NewsHeadlines>> = _headlines

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    private val _selectedCategory = MutableLiveData("general")
    val selectedCategory: LiveData<String> = _selectedCategory

    private val _selectedHeadline = MutableLiveData<NewsHeadlines?>(null)
    val selectedHeadline: LiveData<NewsHeadlines?> = _selectedHeadline

    init {
        loadNews("general")
    }

    fun loadNews(category: String, query: String? = null) {
        _isLoading.value = true
        _selectedCategory.value = category
        _errorMessage.value = null
        viewModelScope.launch {
            when (val result = repository.getNewsHeadlines(category, query)) {
                is Resource.Success -> {
                    _headlines.value = result.data ?: emptyList()
                    if (_headlines.value?.isEmpty() == true) {
                        _errorMessage.value = Strings.NO_DATA_FOUND
                    }
                }
                is Resource.Error -> {
                    _headlines.value = emptyList()
                    _errorMessage.value = result.message ?: Strings.UNKNOWN_ERROR
                }
                else -> {}
            }
            _isLoading.value = false
        }
    }

    fun searchNews(query: String) {
        loadNews(_selectedCategory.value ?: "general", query)
    }

    fun selectHeadline(headline: NewsHeadlines) {
        _selectedHeadline.value = headline
    }
}