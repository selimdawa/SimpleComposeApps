package com.flatcode.simplecomposeapps.news2.data.repositories

import com.flatcode.simplecomposeapps.news2.data.local.NewsDao
import com.flatcode.simplecomposeapps.news2.data.remote.NewsApiServices
import com.flatcode.simplecomposeapps.news2.models.NewsResponse
import com.flatcode.simplecomposeapps.utils.DATA
import com.flatcode.simplecomposeapps.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class EverythingRepository @Inject constructor(
    private val api: NewsApiServices,
    private val dao: NewsDao
) {

    fun getEverything(query: String) = flow {
        emit(Resource.Loading())
        try {
            val response = api.getEverything(query)
            dao.insertEverything(response.articles)
            emit(Resource.Success(response))
        } catch (_: Exception) {
            val cached = dao.getEverything()
            if (cached.isNotEmpty()) {
                emit(Resource.Success(NewsResponse("ok", cached.size, cached)))
            } else {
                emit(Resource.Error(DATA.FAILED_LOAD_DATA))
            }
        }
    }.flowOn(Dispatchers.IO)

    fun getTopArticles(country: String) = flow {
        emit(Resource.Loading())
        try {
            val response = api.getTopArticles(country)
            dao.insertTopArticles(response.articles)
            emit(Resource.Success(response))
        } catch (_: Exception) {
            val cached = dao.getTopArticles()
            if (cached.isNotEmpty()) {
                emit(Resource.Success(NewsResponse("ok", cached.size, cached)))
            } else {
                emit(Resource.Error(DATA.FAILED_LOAD_DATA))
            }
        }
    }.flowOn(Dispatchers.IO)
}