package com.flatcode.simplecomposeapps.crypto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.crypto.db.dao.SettingsDao
import com.flatcode.simplecomposeapps.crypto.db.entity.CryptoSettingsEntity
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
    private val repository: DetailRepository,
    private val settingsDao: SettingsDao
) : ViewModel() {

    fun saveLastVisited(id: Int, symbol: String) {
        viewModelScope.launch {
            settingsDao.saveSettings(CryptoSettingsEntity(coinId = id, coinSymbol = symbol))
        }
    }

    private val _cryptoDetail = MutableStateFlow<CoinDetail?>(null)
    val cryptoDetail: StateFlow<CoinDetail?> = _cryptoDetail

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableSharedFlow<String?>()
    val error: SharedFlow<String?> = _error

    fun getCryptoDetail(apiKey: String, id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = repository.getCryptoDetail(apiKey, id)) {
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