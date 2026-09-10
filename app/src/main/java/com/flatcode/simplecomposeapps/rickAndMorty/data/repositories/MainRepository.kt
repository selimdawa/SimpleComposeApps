package com.flatcode.simplecomposeapps.rickAndMorty.data.repositories

import com.flatcode.simplecomposeapps.rickAndMorty.data.local.RickAndMortyDao
import com.flatcode.simplecomposeapps.rickAndMorty.data.models.Info
import com.flatcode.simplecomposeapps.rickAndMorty.data.models.RickAndMortyResponse
import com.flatcode.simplecomposeapps.rickAndMorty.data.remote.ApiService
import com.flatcode.simplecomposeapps.utils.DATA
import com.flatcode.simplecomposeapps.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val api: ApiService,
    private val dao: RickAndMortyDao
) {

    fun getCharacters(page: Int? = null) = flow {
        emit(Resource.Loading())
        try {
            val response = api.getCharacters(page)
            dao.insertCharacters(response.results)
            emit(Resource.Success(response))
        } catch (_: Exception) {
            if (page == null || page == 1) {
                val cached = dao.getCharacters()
                if (cached.isNotEmpty()) {
                    emit(Resource.Success(RickAndMortyResponse(Info(cached.size, 1, null, null), cached)))
                } else {
                    emit(Resource.Error(DATA.FAILED_LOAD_DATA))
                }
            } else {
                emit(Resource.Error(DATA.FAILED_LOAD_DATA))
            }
        }
    }.flowOn(Dispatchers.IO)

    fun getLocations(page: Int? = null) = flow {
        emit(Resource.Loading())
        try {
            val response = api.getLocations(page)
            dao.insertLocations(response.results)
            emit(Resource.Success(response))
        } catch (_: Exception) {
            if (page == null || page == 1) {
                val cached = dao.getLocations()
                if (cached.isNotEmpty()) {
                    emit(Resource.Success(RickAndMortyResponse(Info(cached.size, 1, null, null), cached)))
                } else {
                    emit(Resource.Error(DATA.FAILED_LOAD_DATA))
                }
            } else {
                emit(Resource.Error(DATA.FAILED_LOAD_DATA))
            }
        }
    }.flowOn(Dispatchers.IO)

    fun getEpisodes(page: Int? = null) = flow {
        emit(Resource.Loading())
        try {
            val response = api.getEpisodes(page)
            dao.insertEpisodes(response.results)
            emit(Resource.Success(response))
        } catch (_: Exception) {
            if (page == null || page == 1) {
                val cached = dao.getEpisodes()
                if (cached.isNotEmpty()) {
                    emit(Resource.Success(RickAndMortyResponse(Info(cached.size, 1, null, null), cached)))
                } else {
                    emit(Resource.Error(DATA.FAILED_LOAD_DATA))
                }
            } else {
                emit(Resource.Error(DATA.FAILED_LOAD_DATA))
            }
        }
    }.flowOn(Dispatchers.IO)
}