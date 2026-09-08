package com.flatcode.simplecomposeapps.crypto.ui.home

import com.flatcode.simplecomposeapps.utils.BaseRepository
import com.flatcode.simplecomposeapps.crypto.db.dao.CoinDao
import com.flatcode.simplecomposeapps.crypto.db.entity.CoinEntity
import com.flatcode.simplecomposeapps.crypto.model.home.CryptoResponse
import com.flatcode.simplecomposeapps.crypto.network.CryptoApi
import com.flatcode.simplecomposeapps.utils.Resource
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val api: CryptoApi,
    private val coinDao: CoinDao
) : BaseRepository() {

    suspend fun getLatestCrypto(
        apiKey: String,
        limit: String,
        start: String
    ): Resource<CryptoResponse> {
        val result = safeApiCall { api.getLatestCrypto(apiKey, limit, start) }
        
        if (result is Resource.Success) {
            result.data?.data?.let { coins ->
                val entities = coins.map { coin ->
                    CoinEntity(
                        id = coin.id ?: 0,
                        name = coin.name ?: "",
                        symbol = coin.symbol ?: "",
                        price = coin.quote?.usd?.price ?: 0.0
                    )
                }
                coinDao.insertCoins(entities)
            }
        }
        
        return result
    }

    suspend fun getCachedCoins(): List<CoinEntity> {
        return coinDao.getAllCoins()
    }
}