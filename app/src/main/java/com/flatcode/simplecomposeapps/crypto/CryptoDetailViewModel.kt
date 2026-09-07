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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoDetailViewModel @Inject constructor(
    private val repository: DetailRepository
) : ViewModel() {

    val cryptoDetail: StateFlow<CoinDetail?>
        field = MutableStateFlow<CoinDetail?>(null)

    val isLoading: StateFlow<Boolean>
        field = MutableStateFlow(false)

    val error: SharedFlow<String?>
        field = MutableSharedFlow<String?>()

    fun getCryptoDetail(apiKey: String, id: Int) {
        viewModelScope.launch {
            isLoading.value = true
            when (val result = repository.getCryptoDetail(apiKey, id)) {
                is NetworkResult.Success -> {
                    cryptoDetail.value = result.data
                }

                is NetworkResult.Error -> {
                    error.emit(result.message)
                }

                is NetworkResult.Loading -> {
                    // Handle loading
                }
            }
            isLoading.value = false
        }
    }
}