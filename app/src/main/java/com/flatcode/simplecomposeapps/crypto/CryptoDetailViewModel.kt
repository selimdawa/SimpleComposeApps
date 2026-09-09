package com.flatcode.simplecomposeapps.crypto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.crypto.db.dao.SettingsDao
import com.flatcode.simplecomposeapps.crypto.db.entity.CryptoSettingsEntity
import com.flatcode.simplecomposeapps.crypto.model.detail.CoinDetail
import com.flatcode.simplecomposeapps.crypto.ui.detail.DetailRepository
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _cryptoDetail = MutableLiveData<CoinDetail?>(null)
    val cryptoDetail: LiveData<CoinDetail?> = _cryptoDetail

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    fun getCryptoDetail(apiKey: String, id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = repository.getCryptoDetail(apiKey, id)) {
                is Resource.Success -> {
                    _cryptoDetail.value = result.data
                }

                is Resource.Error -> {
                    _error.value = result.message
                }

                else -> {}
            }
            _isLoading.value = false
        }
    }
}