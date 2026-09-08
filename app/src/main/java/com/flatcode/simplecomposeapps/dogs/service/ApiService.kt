package com.flatcode.simplecomposeapps.dogs.service

import com.flatcode.simplecomposeapps.utils.DATA
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiService @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getBreedImages(breed: String): DogApi {
        return client.get("${DATA.BASE_URL_DOGS}$breed/images").body()
    }

    suspend fun getSubBreedImages(
        breed: String,
        subBreed: String,
    ): DogApi {
        return client.get("${DATA.BASE_URL_DOGS}$breed/$subBreed/images").body()
    }
}

@Serializable
data class DogApi(
    @SerialName("message") val images: List<String>
)
