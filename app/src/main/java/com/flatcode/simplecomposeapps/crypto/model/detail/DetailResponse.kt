package com.flatcode.simplecomposeapps.crypto.model.detail

import com.google.gson.annotations.SerializedName

data class DetailResponse(
    @SerializedName("data") val data: Map<String, CoinDetail>?,
    @SerializedName("status") val status: Status?
)

data class CoinDetail(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("symbol") val symbol: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("logo") val logo: String?
)

data class Status(
    @SerializedName("error_message") val errorMessage: String?
)