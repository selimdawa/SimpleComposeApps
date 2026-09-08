package com.flatcode.simplecomposeapps.crypto.model.home

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CryptoResponse(
    @SerialName("data") val data: List<Data>?,
    @SerialName("status") val status: Status?
)

@Serializable
data class Data(
    @SerialName("id") val id: Int?,
    @SerialName("name") val name: String?,
    @SerialName("symbol") val symbol: String?,
    @SerialName("quote") val quote: Quote?
)

@Serializable
data class Quote(
    @SerialName("USD") val usd: Usd?
)

@Serializable
data class Usd(
    @SerialName("price") val price: Double?
)

@Serializable
data class Status(
    @SerialName("error_message") val errorMessage: String?
)