package com.flatcode.simplecomposeapps.crypto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.crypto.model.home.Data
import com.flatcode.simplecomposeapps.crypto.ui.home.HomeRepository
import com.flatcode.simplecomposeapps.crypto.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoHomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    private val _cryptoList = MutableStateFlow<List<Data>>(emptyList())
    val cryptoList: StateFlow<List<Data>> = _cryptoList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableSharedFlow<String?>()
    val error: SharedFlow<String?> = _error.asSharedFlow()

    private var currentPage = 1

    fun getData(apiKey: String, limit: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getLatestCrypto(apiKey, limit, currentPage.toString())
            handleResult(result)
            _isLoading.value = false
        }
    }

    fun loadNextPage(apiKey: String) {
        currentPage += 10
        getData(apiKey, "10")
    }

    fun isFirstPage() = currentPage == 1

    private fun handleResult(result: NetworkResult<com.flatcode.simplecomposeapps.crypto.model.home.CryptoResponse>) {
        when (result) {
            is NetworkResult.Success -> {
                val newList = _cryptoList.value.toMutableList()
                result.data?.data?.let { newList.addAll(it) }
                _cryptoList.value = newList
            }
            is NetworkResult.Error -> {
                viewModelScope.launch { _error.emit(result.message) }
            }
            is NetworkResult.Loading -> {
                // Handle loading if needed
            }
        }
    }
}