package com.flatcode.simplecomposeapps.crypto.ui.home

import com.flatcode.simplecomposeapps.crypto.base.BaseRepository
import com.flatcode.simplecomposeapps.crypto.model.home.CryptoResponse
import com.flatcode.simplecomposeapps.crypto.network.CryptoApi
import com.flatcode.simplecomposeapps.crypto.utils.NetworkResult
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val api: CryptoApi
) : BaseRepository() {

    suspend fun getLatestCrypto(
        apiKey: String,
        limit: String,
        start: String
    ): NetworkResult<CryptoResponse> {
        return safeApiCall { api.getLatestCrypto(apiKey, limit, start) }
    }
}