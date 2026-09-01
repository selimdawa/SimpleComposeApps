package com.flatcode.simplecomposeapps.rickAndMorty.data.repositories

import com.flatcode.simplecomposeapps.rickAndMorty.data.base.BaseRepository
import com.flatcode.simplecomposeapps.rickAndMorty.data.remote.ApiService
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val api: ApiService
) : BaseRepository() {

    fun getCharacters(page: Int? = null) = doRequest {
        api.getCharacters(page)
    }

    fun getLocations(page: Int? = null) = doRequest {
        api.getLocations(page)
    }

    fun getEpisodes(page: Int? = null) = doRequest {
        api.getEpisodes(page)
    }
}