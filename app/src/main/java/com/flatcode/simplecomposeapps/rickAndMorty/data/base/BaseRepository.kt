package com.flatcode.simplecomposeapps.rickAndMorty.data.base

import com.flatcode.simplecomposeapps.rickAndMorty.utils.Resource
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