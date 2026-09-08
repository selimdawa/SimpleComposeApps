package com.flatcode.simplecomposeapps.stockmarket.network

import com.flatcode.simplecomposeapps.utils.DATA
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockApi @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getListings(
        apiKey: String = DATA.API_KEY_STOCK
    ): HttpResponse {
        return client.get("${DATA.BASE_URL_STOCK}query") {
            parameter("function", "LISTING_STATUS")
            parameter("apikey", apiKey)
        }
    }
}