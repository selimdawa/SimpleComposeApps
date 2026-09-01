package com.flatcode.simplecomposeapps.movies.data.room.repository

import com.flatcode.simplecomposeapps.movies.data.room.dao.MoviesDao
import com.flatcode.simplecomposeapps.movies.models.MovieItemModel
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
}