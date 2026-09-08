package com.flatcode.simplecomposeapps.rickAndMorty.data.remote

import com.flatcode.simplecomposeapps.rickAndMorty.data.models.Character
import com.flatcode.simplecomposeapps.rickAndMorty.data.models.Episode
import com.flatcode.simplecomposeapps.rickAndMorty.data.models.Location
import com.flatcode.simplecomposeapps.rickAndMorty.data.models.RickAndMortyResponse
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