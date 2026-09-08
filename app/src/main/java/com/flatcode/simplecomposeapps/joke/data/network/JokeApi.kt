package com.flatcode.simplecomposeapps.joke.data.network

import com.flatcode.simplecomposeapps.joke.model.JokeResponse
import com.flatcode.simplecomposeapps.utils.DATA
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JokeApi @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getJokes(
        category: String,
        amount: Int = 10
    ): JokeResponse {
        return client.get("${DATA.JOKE_URL}$category") {
            parameter("amount", amount)
        }.body()
    }
}