package com.flatcode.simplecomposeapps.crypto.ui.detail

import com.flatcode.simplecomposeapps.utils.BaseRepository
import com.flatcode.simplecomposeapps.crypto.db.dao.CoinDetailDao
import com.flatcode.simplecomposeapps.crypto.db.entity.CoinDetailEntity
import com.flatcode.simplecomposeapps.crypto.model.detail.CoinDetail
import com.flatcode.simplecomposeapps.crypto.network.CryptoApi
import com.flatcode.simplecomposeapps.utils.Resource
import javax.inject.Inject

class DetailRepository @Inject constructor(
    private val api: CryptoApi,
    private val coinDetailDao: CoinDetailDao
) : BaseRepository() {

    suspend fun getCryptoDetail(apiKey: String, id: Int): Resource<CoinDetail> {
        // Try to get from database first
        val cachedDetail = coinDetailDao.getCoinDetail(id)
        if (cachedDetail != null) {
            return Resource.Success(
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
            is Resource.Success -> {
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
                    Resource.Success(coinDetail)
                } else {
                    Resource.Error("Coin details not found for id: $id")
                }
            }
            is Resource.Error -> Resource.Error(result.message ?: "An error occurred")
            is Resource.Loading -> Resource.Loading()
            else -> Resource.Idle
        }
    }
}