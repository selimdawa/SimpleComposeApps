package com.flatcode.simplecomposeapps.crypto.ui.detail

import com.flatcode.simplecomposeapps.crypto.base.BaseRepository
import com.flatcode.simplecomposeapps.crypto.model.detail.CoinDetail
import com.flatcode.simplecomposeapps.crypto.network.CryptoApi
import com.flatcode.simplecomposeapps.crypto.utils.NetworkResult
import javax.inject.Inject

class DetailRepository @Inject constructor(
    private val api: CryptoApi
) : BaseRepository() {

    suspend fun getCryptoDetail(apiKey: String, symbol: String): NetworkResult<CoinDetail> {
        val result = safeApiCall { api.getCryptoDetail(apiKey, symbol) }
        return when (result) {
            is NetworkResult.Success -> {
                val coinDetail = result.data?.data?.get(symbol)
                if (coinDetail != null) {
                    NetworkResult.Success(coinDetail)
                } else {
                    NetworkResult.Error("Coin details not found for symbol: $symbol")
                }
            }
            is NetworkResult.Error -> NetworkResult.Error(result.message ?: "An error occurred")
            is NetworkResult.Loading -> NetworkResult.Loading()
        }
    }
}