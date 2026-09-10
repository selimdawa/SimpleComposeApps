package com.flatcode.simplecomposeapps.pokemon.network

import com.flatcode.simplecomposeapps.pokemon.model.PokeModel
import com.flatcode.simplecomposeapps.pokemon.model.PokeModelDetails
import com.flatcode.simplecomposeapps.utils.DATA
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiService @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getPokemon(
        limit: Int,
        offset: Int
    ): PokeModel {
        return client.get("${DATA.BASE_URL_POKE}pokemon") {
            parameter("limit", limit)
            parameter("offset", offset)
        }.body()
    }

    suspend fun getPokemonDetails(
        id: Int
    ): PokeModelDetails {
        return client.get("${DATA.BASE_URL_POKE}pokemon/$id").body()
    }
}