package com.flatcode.simplecomposeapps.dictionary.service

import com.flatcode.simplecomposeapps.utils.DATA
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DictionaryAPI @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getDefinition(
        word: String,
        apiKey: String
    ): List<DictionaryResponse> {
        return client.get("${DATA.DICTIONARY_BASIC_URL}$word") {
            parameter("key", apiKey)
        }.body()
    }
}

@Serializable
data class DictionaryResponse(
    @SerialName("shortdef") val shortdef: List<String>?
)
