package com.flatcode.simplecomposeapps.crypto.base

import com.flatcode.simplecomposeapps.crypto.utils.NetworkResult
import com.flatcode.simplecomposeapps.ui.theme.Strings

abstract class BaseRepository {
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): NetworkResult<T> {
        return try {
            val response = apiCall()
            NetworkResult.Success(response)
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: Strings.UNKNOWN_ERROR)
        }
    }
}
