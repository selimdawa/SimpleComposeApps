package com.flatcode.simplecomposeapps.movies.db

import com.flatcode.simplecomposeapps.movies.model.MovieItemModel
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    val allMovies: Flow<List<MovieItemModel>>
    suspend fun insertMovie(movie: MovieItemModel)
    suspend fun deleteMovie(movie: MovieItemModel)
    suspend fun isMovieFavorite(id: Int): Boolean
}