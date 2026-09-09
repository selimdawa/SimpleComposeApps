package com.flatcode.simplecomposeapps.rickAndMorty.data.repositories

import com.flatcode.simplecomposeapps.rickAndMorty.data.remote.ApiService
import com.flatcode.simplecomposeapps.utils.DATA
import com.flatcode.simplecomposeapps.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val api: ApiService
) {

    fun getCharacters(page: Int? = null) = flow {
        emit(Resource.Loading())
        try {
            val response = api.getCharacters(page)
            emit(Resource.Success(response))
        } catch (_: Exception) {
            emit(Resource.Error(DATA.FAILED_LOAD_DATA))
        }
    }.flowOn(Dispatchers.IO)

    fun getLocations(page: Int? = null) = flow {
        emit(Resource.Loading())
        try {
            val response = api.getLocations(page)
            emit(Resource.Success(response))
        } catch (_: Exception) {
            emit(Resource.Error(DATA.FAILED_LOAD_DATA))
        }
    }.flowOn(Dispatchers.IO)

    fun getEpisodes(page: Int? = null) = flow {
        emit(Resource.Loading())
        try {
            val response = api.getEpisodes(page)
            emit(Resource.Success(response))
        } catch (_: Exception) {
            emit(Resource.Error(DATA.FAILED_LOAD_DATA))
        }
    }.flowOn(Dispatchers.IO)
}