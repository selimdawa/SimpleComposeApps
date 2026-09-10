package com.flatcode.simplecomposeapps.movies.network

import com.flatcode.simplecomposeapps.movies.model.MovieItemModel
import com.flatcode.simplecomposeapps.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieApi: MovieApi
) {
    suspend fun getMovies(): Resource<List<MovieItemModel>> = withContext(Dispatchers.IO) {
        try {
            val response = movieApi.getMovies()
            Resource.Success(response.results)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }
}