package com.flatcode.simplecomposeapps.news2.base

import com.flatcode.simplecomposeapps.news2.common.Resource
import kotlinx.coroutines.flow.flow
import retrofit2.Response

abstract class BaseRepository {
    protected fun <T> doRequest(apiCall: suspend () -> Response<T>) = flow {
        emit(Resource.Loading())
        try {
            val response = apiCall()
            if (response.isSuccessful && response.body() != null) {
                emit(Resource.Success(response.body()!!))
            } else {
                emit(Resource.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }
}