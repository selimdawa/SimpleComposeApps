package com.flatcode.simplecomposeapps.crypto.ui.home

import com.flatcode.simplecomposeapps.crypto.db.dao.CoinDao
import com.flatcode.simplecomposeapps.crypto.db.entity.CoinEntity
import com.flatcode.simplecomposeapps.crypto.model.home.CryptoResponse
import com.flatcode.simplecomposeapps.crypto.network.CryptoApi
import com.flatcode.simplecomposeapps.utils.DATA
import com.flatcode.simplecomposeapps.utils.Resource
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val api: CryptoApi,
    private val coinDao: CoinDao
) {

    suspend fun getLatestCrypto(
        apiKey: String,
        limit: String,
        start: String
    ): Resource<CryptoResponse> {
        return try {
            val response = api.getLatestCrypto(apiKey, limit, start)
            response.data?.let { coins ->
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
            Resource.Success(response)
        } catch (_: Exception) {
            Resource.Error(DATA.FAILED_LOAD_DATA)
        }
    }

    suspend fun getCachedCoins(): List<CoinEntity> {
        return coinDao.getAllCoins()
    }
}