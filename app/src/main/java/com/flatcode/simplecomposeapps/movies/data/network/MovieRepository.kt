package com.flatcode.simplecomposeapps.movies.data.network

import com.flatcode.simplecomposeapps.movies.models.MoviesModel
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieApi: MovieApi) {
    suspend fun getMovies(): MoviesModel {
        return movieApi.getMovies()
    }
}