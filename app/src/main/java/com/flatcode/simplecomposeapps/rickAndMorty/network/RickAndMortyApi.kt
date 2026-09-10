package com.flatcode.simplecomposeapps.rickAndMorty.network

import com.flatcode.simplecomposeapps.rickAndMorty.model.Character
import com.flatcode.simplecomposeapps.rickAndMorty.model.Episode
import com.flatcode.simplecomposeapps.rickAndMorty.model.Location
import com.flatcode.simplecomposeapps.rickAndMorty.model.RickAndMortyResponse
import com.flatcode.simplecomposeapps.utils.DATA
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RickAndMortyApi @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getCharacters(
        page: Int? = null
    ): RickAndMortyResponse<Character> {
        return client.get("${DATA.BASE_URL_RICK_AND_MORTY}character") {
            page?.let { parameter("page", it) }
        }.body()
    }

    suspend fun getLocations(
        page: Int? = null
    ): RickAndMortyResponse<Location> {
        return client.get("${DATA.BASE_URL_RICK_AND_MORTY}location") {
            page?.let { parameter("page", it) }
        }.body()
    }

    suspend fun getEpisodes(
        page: Int? = null
    ): RickAndMortyResponse<Episode> {
        return client.get("${DATA.BASE_URL_RICK_AND_MORTY}episode") {
            page?.let { parameter("page", it) }
        }.body()
    }
}