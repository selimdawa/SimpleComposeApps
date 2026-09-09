package com.flatcode.simplecomposeapps.crypto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.crypto.db.dao.SettingsDao
import com.flatcode.simplecomposeapps.crypto.db.entity.CryptoSettingsEntity
import com.flatcode.simplecomposeapps.crypto.model.home.CryptoResponse
import com.flatcode.simplecomposeapps.crypto.model.home.Data
import com.flatcode.simplecomposeapps.crypto.model.home.Quote
import com.flatcode.simplecomposeapps.crypto.model.home.Usd
import com.flatcode.simplecomposeapps.crypto.ui.home.HomeRepository
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoHomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val settingsDao: SettingsDao
) : ViewModel() {

    private val _lastVisitedCoinId = MutableLiveData<Int?>(null)
    val lastVisitedCoinId: LiveData<Int?> = _lastVisitedCoinId

    init {
        observeSettings()
    }

    private fun observeSettings() {
        viewModelScope.launch {
            settingsDao.getSettings().collectLatest { settings ->
                _lastVisitedCoinId.postValue(settings?.coinId)
            }
        }
    }

    fun saveLastVisited(id: Int, symbol: String) {
        viewModelScope.launch {
            settingsDao.saveSettings(CryptoSettingsEntity(coinId = id, coinSymbol = symbol))
        }
    }

    private val _cryptoList = MutableLiveData<List<Data>>(emptyList())
    val cryptoList: LiveData<List<Data>> = _cryptoList

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    private var currentPage = 1

    fun getData(apiKey: String, limit: String) {
        viewModelScope.launch {
            _isLoading.value = true

            if (currentPage == 1 && (_cryptoList.value ?: emptyList()).isEmpty()) {
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

    private fun handleResult(result: Resource<CryptoResponse>) {
        when (result) {
            is Resource.Success -> {
                val newList = (_cryptoList.value ?: emptyList()).toMutableList()
                result.data?.data?.let { newList.addAll(it) }
                _cryptoList.value = newList
            }

            is Resource.Error -> {
                _error.value = result.message
            }

            else -> {}
        }
    }
}