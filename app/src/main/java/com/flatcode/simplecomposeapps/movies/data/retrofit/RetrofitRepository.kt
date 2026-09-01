package com.flatcode.simplecomposeapps.movies.data.retrofit

import com.flatcode.simplecomposeapps.movies.data.retrofit.api.ApiService
import com.flatcode.simplecomposeapps.movies.models.MoviesModel
import retrofit2.Response
import javax.inject.Inject

class RetrofitRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getMovies(): Response<MoviesModel> {
        return apiService.getMovies()
    }
}