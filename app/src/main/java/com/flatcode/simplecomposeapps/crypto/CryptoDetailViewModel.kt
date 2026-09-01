package com.flatcode.simplecomposeapps.crypto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.crypto.model.detail.CoinDetail
import com.flatcode.simplecomposeapps.crypto.ui.detail.DetailRepository
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
class CryptoDetailViewModel @Inject constructor(
    private val repository: DetailRepository
) : ViewModel() {

    private val _cryptoDetail = MutableStateFlow<CoinDetail?>(null)
    val cryptoDetail: StateFlow<CoinDetail?> = _cryptoDetail.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableSharedFlow<String?>()
    val error: SharedFlow<String?> = _error.asSharedFlow()

    fun getCryptoDetail(apiKey: String, symbol: String) {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = repository.getCryptoDetail(apiKey, symbol)) {
                is NetworkResult.Success -> {
                    _cryptoDetail.value = result.data
                }
                is NetworkResult.Error -> {
                    _error.emit(result.message)
                }
                is NetworkResult.Loading -> {
                    // Handle loading
                }
            }
            _isLoading.value = false
        }
    }
}