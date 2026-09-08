package com.flatcode.simplecomposeapps.crypto.network

import com.flatcode.simplecomposeapps.crypto.model.detail.DetailResponse
import com.flatcode.simplecomposeapps.crypto.model.home.CryptoResponse
import com.flatcode.simplecomposeapps.utils.DATA
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CryptoApi @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getLatestCrypto(
        apiKey: String,
        limit: String,
        start: String
    ): CryptoResponse {
        return client.get("${DATA.BASE_URL_CRYPTO}${DATA.LATEST_CRYPTO}") {
            header("X-CMC_PRO_API_KEY", apiKey)
            parameter("limit", limit)
            parameter("start", start)
        }.body()
    }

    suspend fun getCryptoDetail(
        apiKey: String,
        id: Int
    ): DetailResponse {
        return client.get("${DATA.BASE_URL_CRYPTO}${DATA.INFO_CRYPTO}") {
            header("X-CMC_PRO_API_KEY", apiKey)
            parameter("id", id)
        }.body()
    }
}