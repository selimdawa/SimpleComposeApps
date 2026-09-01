package com.flatcode.simplecomposeapps.rickAndMorty.data.remote

import com.flatcode.simplecomposeapps.rickAndMorty.data.models.Character
import com.flatcode.simplecomposeapps.rickAndMorty.data.models.Episode
import com.flatcode.simplecomposeapps.rickAndMorty.data.models.Location
import com.flatcode.simplecomposeapps.rickAndMorty.data.models.RickAndMortyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int? = null): Response<RickAndMortyResponse<Character>>

    @GET("location")
    suspend fun getLocations(@Query("page") page: Int? = null): Response<RickAndMortyResponse<Location>>

    @GET("episode")
    suspend fun getEpisodes(@Query("page") page: Int? = null): Response<RickAndMortyResponse<Episode>>
}