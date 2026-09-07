package com.flatcode.simplecomposeapps.crypto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoHomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    val cryptoList: StateFlow<List<Data>>
        field = MutableStateFlow<List<Data>>(emptyList())

    val isLoading: StateFlow<Boolean>
        field = MutableStateFlow(false)

    val error: SharedFlow<String?>
        field = MutableSharedFlow<String?>()

    private var currentPage = 1

    fun getData(apiKey: String, limit: String) {
        viewModelScope.launch {
            isLoading.value = true

            // Try cache first if it's the first page
            if (currentPage == 1 && cryptoList.value.isEmpty()) {
                val cached = repository.getCachedCoins()
                if (cached.isNotEmpty()) {
                    cryptoList.value = cached.map { entity ->
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
            isLoading.value = false
        }
    }

    fun loadNextPage(apiKey: String) {
        currentPage += 10
        getData(apiKey, "10")
    }

    private fun handleResult(result: NetworkResult<com.flatcode.simplecomposeapps.crypto.model.home.CryptoResponse>) {
        when (result) {
            is NetworkResult.Success -> {
                val newList = cryptoList.value.toMutableList()
                result.data?.data?.let { newList.addAll(it) }
                cryptoList.value = newList
            }

            is NetworkResult.Error -> {
                viewModelScope.launch { error.emit(result.message) }
            }

            is NetworkResult.Loading -> {
                // Handle loading if needed
            }
        }
    }
}