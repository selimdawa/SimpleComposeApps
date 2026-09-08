package com.flatcode.simplecomposeapps.stockmarket.data.remote

import com.flatcode.simplecomposeapps.stockmarket.util.Constants
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
        apiKey: String = Constants.API_KEY
    ): HttpResponse {
        return client.get("${Constants.BASE_URL}query") {
            parameter("function", "LISTING_STATUS")
            parameter("apikey", apiKey)
        }
    }
}