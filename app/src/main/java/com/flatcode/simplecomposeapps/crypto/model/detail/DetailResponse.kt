package com.flatcode.simplecomposeapps.crypto.model.detail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailResponse(
    @SerialName("data") val data: Map<String, CoinDetail>?,
    @SerialName("status") val status: Status?
)

@Serializable
data class CoinDetail(
    @SerialName("id") val id: Int?,
    @SerialName("name") val name: String?,
    @SerialName("symbol") val symbol: String?,
    @SerialName("description") val description: String?,
    @SerialName("logo") val logo: String?
)

@Serializable
data class Status(
    @SerialName("error_message") val errorMessage: String?
)
