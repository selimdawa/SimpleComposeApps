package com.flatcode.simplecomposeapps.movies.data.room.repository

import com.flatcode.simplecomposeapps.movies.models.MovieItemModel
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    val allMovies: Flow<List<MovieItemModel>>
    suspend fun insertMovie(movie: MovieItemModel)
    suspend fun deleteMovie(movie: MovieItemModel)
}