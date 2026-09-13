package com.flatcode.simplecomposeapps.crypto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.crypto.model.detail.CoinDetail
import com.flatcode.simplecomposeapps.crypto.ui.detail.DetailRepository
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CryptoDetailViewModel @Inject constructor(
    private val repository: DetailRepository
) : ViewModel() {


    private val _cryptoDetail = MutableStateFlow<CoinDetail?>(null)
    val cryptoDetail: StateFlow<CoinDetail?> = _cryptoDetail.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun getCryptoDetail(apiKey: String, id: Int) {
        Timber.d("Getting crypto detail for id: %d", id)
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = repository.getCryptoDetail(apiKey, id)) {
                is Resource.Success -> {
                    _cryptoDetail.value = result.data
                    Timber.d("Successfully fetched crypto detail")
                }

                is Resource.Error -> {
                    _error.value = result.message
                    Timber.e("Error fetching crypto detail: %s", result.message)
                }

                else -> {}
            }
            _isLoading.value = false
        }
    }
}
