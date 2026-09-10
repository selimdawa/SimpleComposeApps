package com.flatcode.simplecomposeapps.movies.network

import com.flatcode.simplecomposeapps.movies.db.MoviesDao
import com.flatcode.simplecomposeapps.movies.model.MovieItemModel
import com.flatcode.simplecomposeapps.utils.DATA
import com.flatcode.simplecomposeapps.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieApi: MovieApi,
    private val moviesDao: MoviesDao
) {
    suspend fun getMovies(): Resource<List<MovieItemModel>> = withContext(Dispatchers.IO) {
        try {
            val response = movieApi.getMovies()
            response.results.forEach { movie ->
                val existing = moviesDao.getMovieById(movie.id)
                moviesDao.insertMovie(movie.copy(isFavorite = existing?.isFavorite ?: false))
            }
            Resource.Success(response.results)
        } catch (_: Exception) {
            val cached = moviesDao.getMovies()
            if (cached.isNotEmpty()) {
                Resource.Success(cached)
            } else {
                Resource.Error(DATA.FAILED_LOAD_DATA)
            }
        }
    }
}