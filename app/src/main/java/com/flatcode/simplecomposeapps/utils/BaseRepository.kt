package com.flatcode.simplecomposeapps.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

abstract class BaseRepository {
    protected fun <T> doRequest(apiCall: suspend () -> T): Flow<Resource<T>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiCall()
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)

    protected fun <ResultType, RequestType> networkBoundResource(
        query: () -> Flow<ResultType>,
        fetch: suspend () -> RequestType,
        saveFetchResult: suspend (RequestType) -> Unit,
        shouldFetch: (ResultType) -> Boolean = { true },
        onFetchFailed: (Throwable) -> Unit = { }
    ) = flow {
        emit(Resource.Loading())
        val data = query().first()

        val flow = if (shouldFetch(data)) {
            emit(Resource.Loading(data = data))

            try {
                saveFetchResult(fetch())
                query().map { Resource.Success(it) }
            } catch (throwable: Throwable) {
                onFetchFailed(throwable)
                query().map { Resource.Error(throwable.localizedMessage ?: "Unknown error", it) }
            }
        } else {
            query().map { Resource.Success(it) }
        }

        emitAll(flow)
    }.flowOn(Dispatchers.IO)

    protected suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(apiCall())
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Unknown error")
        }
    }
}
