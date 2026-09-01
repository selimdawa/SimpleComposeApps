package com.flatcode.simplecomposeapps.crypto.base

import com.flatcode.simplecomposeapps.crypto.model.errorResponse.ErrorResponse
import com.flatcode.simplecomposeapps.crypto.utils.NetworkResult
import com.google.gson.Gson
import retrofit2.Response

abstract class BaseRepository {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return NetworkResult.Success(body)
                }
            }

            val errorBody = response.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            val errorMessage = errorResponse?.status?.errorMessage ?: "An unknown error occurred"

            return NetworkResult.Error(errorMessage)
        } catch (e: Exception) {
            return NetworkResult.Error(e.message ?: "An unknown error occurred")
        }
    }
}