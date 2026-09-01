package com.flatcode.simplecomposeapps.movies.data.retrofit.api

import com.flatcode.simplecomposeapps.movies.models.MoviesModel
import com.flatcode.simplecomposeapps.utils.DATA
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET(DATA.POPULAR_MOVIES)
    suspend fun getMovies(): Response<MoviesModel>
}