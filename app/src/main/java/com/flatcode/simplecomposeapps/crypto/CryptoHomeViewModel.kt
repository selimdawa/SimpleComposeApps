package com.flatcode.simplecomposeapps.crypto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.crypto.model.home.CryptoResponse
import com.flatcode.simplecomposeapps.crypto.model.home.Data
import com.flatcode.simplecomposeapps.crypto.model.home.Quote
import com.flatcode.simplecomposeapps.crypto.model.home.Usd
import com.flatcode.simplecomposeapps.crypto.ui.home.CryptoRepository
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CryptoHomeViewModel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {

    private val _cryptoList = MutableStateFlow<List<Data>>(emptyList())
    val cryptoList: StateFlow<List<Data>> = _cryptoList.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private var currentPage = 1

    fun getData(apiKey: String, limit: String) {
        viewModelScope.launch {
            Timber.d("Getting crypto data for page: %d", currentPage)
            _isLoading.value = true

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

    private fun handleResult(result: Resource<CryptoResponse>) {
        when (result) {
            is Resource.Success -> {
                val newList = if (currentPage == 1) mutableListOf() else _cryptoList.value.toMutableList()
                result.data?.data?.let { coins ->
                    val currentIds = newList.map { it.id }.toSet()
                    val distinctNewCoins = coins.filter { it.id !in currentIds }
                    newList.addAll(distinctNewCoins)
                }
                _cryptoList.value = newList
            }

            is Resource.Error -> {
                _error.value = result.message
                Timber.e("Error loading crypto data: %s", result.message)
            }

            else -> {}
        }
    }
}