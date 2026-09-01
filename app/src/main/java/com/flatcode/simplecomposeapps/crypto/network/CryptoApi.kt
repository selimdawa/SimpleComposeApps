package com.flatcode.simplecomposeapps.crypto.network

import com.flatcode.simplecomposeapps.crypto.model.detail.DetailResponse
import com.flatcode.simplecomposeapps.crypto.model.home.CryptoResponse
import com.flatcode.simplecomposeapps.utils.DATA
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CryptoApi {

    @GET(DATA.LATEST_CRYPTO)
    suspend fun getLatestCrypto(
        @Header("X-CMC_PRO_API_KEY") apiKey: String,
        @Query("limit") limit: String,
        @Query("start") start: String
    ): Response<CryptoResponse>

    @GET(DATA.INFO_CRYPTO)
    suspend fun getCryptoDetail(
        @Header("X-CMC_PRO_API_KEY") apiKey: String,
        @Query("symbol") symbol: String
    ): Response<DetailResponse>
}