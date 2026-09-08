package com.flatcode.simplecomposeapps.crypto.model.errorResponse

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    @SerialName("status") val status: Status?
)

@Serializable
data class Status(
    @SerialName("error_code") val errorCode: Int?,
    @SerialName("error_message") val errorMessage: String?
)
