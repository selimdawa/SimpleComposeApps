package com.flatcode.simplecomposeapps.rickAndMorty.data.base

import com.flatcode.simplecomposeapps.rickAndMorty.utils.Resource
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