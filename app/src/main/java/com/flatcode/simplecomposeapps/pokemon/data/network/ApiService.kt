package com.flatcode.simplecomposeapps.pokemon.data.network

import com.flatcode.simplecomposeapps.pokemon.data.model.PokeModel
import com.flatcode.simplecomposeapps.pokemon.data.model.PokeModelDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("pokemon")
    suspend fun getPokemon(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<PokeModel>

    @GET("pokemon/{id}")
    suspend fun getPokemonDetails(
        @Path("id") id: Int
    ): Response<PokeModelDetails>
}