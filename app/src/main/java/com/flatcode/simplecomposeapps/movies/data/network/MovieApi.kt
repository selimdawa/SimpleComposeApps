package com.flatcode.simplecomposeapps.movies.data.network

import com.flatcode.simplecomposeapps.movies.models.MoviesModel
import com.flatcode.simplecomposeapps.utils.DATA
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieApi @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getMovies(): MoviesModel {
        return client.get("${DATA.BASE_URL_MOVIES}${DATA.POPULAR_MOVIES}").body()
    }
}