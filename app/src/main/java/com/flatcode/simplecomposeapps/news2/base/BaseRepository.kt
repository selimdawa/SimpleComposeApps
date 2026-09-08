package com.flatcode.simplecomposeapps.news2.base

import com.flatcode.simplecomposeapps.news2.common.Resource
import kotlinx.coroutines.flow.flow

abstract class BaseRepository {
    protected fun <T> doRequest(apiCall: suspend () -> T) = flow {
        emit(Resource.Loading())
        try {
            val response = apiCall()
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }
}