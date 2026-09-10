package com.flatcode.simplecomposeapps.crypto.ui.home

import com.flatcode.simplecomposeapps.crypto.db.dao.CoinDao
import com.flatcode.simplecomposeapps.crypto.db.entity.CoinEntity
import com.flatcode.simplecomposeapps.crypto.model.home.CryptoResponse
import com.flatcode.simplecomposeapps.crypto.model.home.Data
import com.flatcode.simplecomposeapps.crypto.model.home.Quote
import com.flatcode.simplecomposeapps.crypto.model.home.Usd
import com.flatcode.simplecomposeapps.crypto.network.CryptoApi
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.Resource
import timber.log.Timber
import javax.inject.Inject

class CryptoRepository @Inject constructor(
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
        } catch (e: Exception) {
            Timber.e(e, "Error fetching crypto data")
            val cached = coinDao.getAllCoins()
            if (cached.isNotEmpty() && start == "1") {
                val cachedData = cached.map { entity ->
                    Data(
                        id = entity.id,
                        name = entity.name,
                        symbol = entity.symbol,
                        quote = Quote(usd = Usd(price = entity.price))
                    )
                }
                Resource.Success(CryptoResponse(data = cachedData, status = null))
            } else {
                Resource.Error(Strings.FAILED_LOAD_DATA)
            }
        }
    }

    suspend fun getCachedCoins(): List<CoinEntity> {
        return coinDao.getAllCoins()
    }
}