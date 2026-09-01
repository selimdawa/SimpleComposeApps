package com.flatcode.simplecomposeapps.countries.service

import com.flatcode.simplecomposeapps.utils.DATA
import com.flatcode.simplecomposeapps.countries.model.Country
import retrofit2.http.GET

interface CountryAPI {
    @GET(DATA.COUNTRY_GSON)
    suspend fun getCountries(): List<Country>
}