package com.flatcode.simplecomposeapps.movies.db

import com.flatcode.simplecomposeapps.movies.model.MovieItemModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepositoryRealization @Inject constructor(private val moviesDao: MoviesDao) :
    MoviesRepository {
    override val allMovies: Flow<List<MovieItemModel>>
        get() = moviesDao.getAllMovies()

    override suspend fun insertMovie(movie: MovieItemModel) {
        moviesDao.insertMovie(movie)
    }

    override suspend fun deleteMovie(movie: MovieItemModel) {
        moviesDao.deleteMovie(movie)
    }

    override suspend fun isMovieFavorite(id: Int): Boolean {
        return moviesDao.isMovieFavorite(id)
    }
}