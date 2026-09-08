package com.flatcode.simplecomposeapps.randomcatsimage.network

import com.flatcode.simplecomposeapps.utils.DATA
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.Serializable
import javax.inject.Inject
import javax.inject.Singleton

@Serializable
data class CatImageResponse(
    val url: String
)

@Singleton
class CatImageApi @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getRandomImage(): List<CatImageResponse> {
        return client.get(DATA.API_RANDOM_IMAGE).body()
    }
}