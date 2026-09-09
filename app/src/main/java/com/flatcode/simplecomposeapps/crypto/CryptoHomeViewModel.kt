package com.flatcode.simplecomposeapps.crypto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.crypto.model.home.CryptoResponse
import com.flatcode.simplecomposeapps.crypto.model.home.Data
import com.flatcode.simplecomposeapps.crypto.model.home.Quote
import com.flatcode.simplecomposeapps.crypto.model.home.Usd
import com.flatcode.simplecomposeapps.crypto.ui.home.HomeRepository
import com.flatcode.simplecomposeapps.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoHomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

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