package com.flatcode.simplecomposeapps.crypto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.crypto.db.dao.SettingsDao
import com.flatcode.simplecomposeapps.crypto.db.entity.CryptoSettingsEntity
import com.flatcode.simplecomposeapps.crypto.model.home.Data
import com.flatcode.simplecomposeapps.crypto.model.home.Quote
import com.flatcode.simplecomposeapps.crypto.model.home.Usd
import com.flatcode.simplecomposeapps.crypto.ui.home.HomeRepository
import com.flatcode.simplecomposeapps.crypto.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoHomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val settingsDao: SettingsDao
) : ViewModel() {

    private val _lastVisitedCoinId = MutableStateFlow<Int?>(null)
    val lastVisitedCoinId: StateFlow<Int?> = _lastVisitedCoinId

    init {
        observeSettings()
    }

    private fun observeSettings() {
        viewModelScope.launch {
            settingsDao.getSettings().collectLatest { settings ->
                _lastVisitedCoinId.value = settings?.coinId
            }
        }
    }

    fun saveLastVisited(id: Int, symbol: String) {
        viewModelScope.launch {
            settingsDao.saveSettings(CryptoSettingsEntity(coinId = id, coinSymbol = symbol))
        }
    }

    private val _cryptoList = MutableStateFlow<List<Data>>(emptyList())
    val cryptoList: StateFlow<List<Data>> = _cryptoList

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableSharedFlow<String?>()
    val error: SharedFlow<String?> = _error

    private var currentPage = 1

    fun getData(apiKey: String, limit: String) {
        viewModelScope.launch {
            _isLoading.value = true

            // Try cache first if it's the first page
            if (currentPage == 1 && _cryptoList.value.isEmpty()) {
                val cached = repository.getCachedCoins()
                if (cached.isNotEmpty()) {
                    _cryptoList.value = cached.map { entity ->
                        Data(
                            id = entity.id,
                            name = entity.name,
                            symbol = entity.symbol,
                            quote = Quote(usd = Usd(price = entity.price))
                        )
                    }
                }
            }

            val result = repository.getLatestCrypto(apiKey, limit, currentPage.toString())
            handleResult(result)
            _isLoading.value = false
        }
    }

    fun loadNextPage(apiKey: String) {
        currentPage += 10
        getData(apiKey, "10")
    }

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