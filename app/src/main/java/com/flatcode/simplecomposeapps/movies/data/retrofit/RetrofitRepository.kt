package com.flatcode.simplecomposeapps.movies.data.retrofit

import com.flatcode.simplecomposeapps.movies.data.retrofit.api.MovieApi
import com.flatcode.simplecomposeapps.movies.models.MoviesModel
import javax.inject.Inject

class RetrofitRepository @Inject constructor(private val movieApi: MovieApi) {
    suspend fun getMovies(): MoviesModel {
        return movieApi.getMovies()
    }
}
