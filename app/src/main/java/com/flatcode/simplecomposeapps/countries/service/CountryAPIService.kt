package com.flatcode.simplecomposeapps.countries.service

import com.flatcode.simplecomposeapps.utils.DATA
import com.flatcode.simplecomposeapps.countries.model.Country
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CountryAPIService {

    private val api = Retrofit.Builder()
        .baseUrl(DATA.BASE_URL_COUNTRY)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CountryAPI::class.java)

    suspend fun getData(): List<Country> {
        return api.getCountries()
    }
}