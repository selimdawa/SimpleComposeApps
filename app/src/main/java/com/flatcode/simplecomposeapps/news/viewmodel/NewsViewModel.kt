package com.flatcode.simplecomposeapps.news.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.news.data.repository.NewsRepository
import com.flatcode.simplecomposeapps.news.model.NewsHeadlines
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    application: Application,
    private val repository: NewsRepository
) : AndroidViewModel(application) {

    private val _headlines = MutableStateFlow<List<NewsHeadlines>>(emptyList())
    val headlines: StateFlow<List<NewsHeadlines>> = _headlines.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _selectedCategory = MutableStateFlow("general")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _selectedHeadline = MutableStateFlow<NewsHeadlines?>(null)
    val selectedHeadline: StateFlow<NewsHeadlines?> = _selectedHeadline.asStateFlow()

    init {
        loadNews("general")
    }

    fun loadNews(category: String, query: String? = null) {
        Timber.d("Loading news for category: %s, query: %s", category, query)
        _isLoading.value = true
        _selectedCategory.value = category
        _errorMessage.value = null
        viewModelScope.launch {
            when (val result = repository.getNewsHeadlines(category, query)) {
                is Resource.Success -> {
                    _headlines.value = result.data ?: emptyList()
                    if (_headlines.value.isEmpty()) {
                        _errorMessage.value = Strings.NO_DATA_FOUND
                    }
                }
                is Resource.Error -> {
                    _headlines.value = emptyList()
                    _errorMessage.value = result.message ?: Strings.UNKNOWN_ERROR
                    Timber.e("Error loading news: %s", result.message)
                }
                else -> {}
            }
            _isLoading.value = false
        }
    }

    fun searchNews(query: String) {
        loadNews(_selectedCategory.value, query)
    }

    fun selectHeadline(headline: NewsHeadlines) {
        _selectedHeadline.value = headline
    }
}