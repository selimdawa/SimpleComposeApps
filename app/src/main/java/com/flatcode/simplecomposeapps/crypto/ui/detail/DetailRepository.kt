package com.flatcode.simplecomposeapps.crypto.ui.detail

import com.flatcode.simplecomposeapps.crypto.base.BaseRepository
import com.flatcode.simplecomposeapps.crypto.db.dao.CoinDetailDao
import com.flatcode.simplecomposeapps.crypto.db.entity.CoinDetailEntity
import com.flatcode.simplecomposeapps.crypto.model.detail.CoinDetail
import com.flatcode.simplecomposeapps.crypto.network.CryptoApi
import com.flatcode.simplecomposeapps.crypto.utils.NetworkResult
import javax.inject.Inject

class DetailRepository @Inject constructor(
    private val api: CryptoApi,
    private val coinDetailDao: CoinDetailDao
) : BaseRepository() {

    suspend fun getCryptoDetail(apiKey: String, id: Int): NetworkResult<CoinDetail> {
        // Try to get from database first
        val cachedDetail = coinDetailDao.getCoinDetail(id)
        if (cachedDetail != null) {
            return NetworkResult.Success(
                CoinDetail(
                    id = cachedDetail.id,
                    name = cachedDetail.name,
                    symbol = cachedDetail.symbol,
                    description = cachedDetail.description,
                    logo = cachedDetail.logo
                )
            )
        }

        // If not in database, fetch from API
        val result = safeApiCall { api.getCryptoDetail(apiKey, id) }
        return when (result) {
            is NetworkResult.Success -> {
                val coinDetail = result.data?.data?.get(id.toString())
                if (coinDetail != null) {
                    // Save to database
                    coinDetailDao.insertCoinDetail(
                        CoinDetailEntity(
                            id = coinDetail.id ?: id,
                            name = coinDetail.name ?: "",
                            symbol = coinDetail.symbol ?: "",
                            description = coinDetail.description ?: "",
                            logo = coinDetail.logo ?: ""
                        )
                    )
                    NetworkResult.Success(coinDetail)
                } else {
                    NetworkResult.Error("Coin details not found for id: $id")
                }
            }
            is NetworkResult.Error -> NetworkResult.Error(result.message ?: "An error occurred")
            is NetworkResult.Loading -> NetworkResult.Loading()
        }
    }
}