package com.flatcode.simplecomposeapps.countries.service

import com.flatcode.simplecomposeapps.countries.model.Country
import com.flatcode.simplecomposeapps.utils.DATA
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryService @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getCountries(): List<Country> {
        return client.get("${DATA.BASE_URL_COUNTRY}${DATA.COUNTRY_GSON}").body()
    }
}
