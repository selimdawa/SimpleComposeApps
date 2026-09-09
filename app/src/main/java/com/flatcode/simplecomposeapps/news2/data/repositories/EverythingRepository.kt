package com.flatcode.simplecomposeapps.news2.data.repositories

import com.flatcode.simplecomposeapps.news2.data.remote.NewsApiServices
import com.flatcode.simplecomposeapps.utils.DATA
import com.flatcode.simplecomposeapps.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class EverythingRepository @Inject constructor(
    private val api: NewsApiServices
) {

    fun getEverything(query: String) = flow {
        emit(Resource.Loading())
        try {
            val response = api.getEverything(query)
            emit(Resource.Success(response))
        } catch (_: Exception) {
            emit(Resource.Error(DATA.FAILED_LOAD_DATA))
        }
    }.flowOn(Dispatchers.IO)

    fun getTopArticles(country: String) = flow {
        emit(Resource.Loading())
        try {
            val response = api.getTopArticles(country)
            emit(Resource.Success(response))
        } catch (_: Exception) {
            emit(Resource.Error(DATA.FAILED_LOAD_DATA))
        }
    }.flowOn(Dispatchers.IO)
}