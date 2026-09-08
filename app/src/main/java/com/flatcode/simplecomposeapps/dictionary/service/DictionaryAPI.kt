package com.flatcode.simplecomposeapps.dictionary.service

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DictionaryAPI {
    @GET("{word}")
    suspend fun getDefinition(
        @Path("word") word: String,
        @Query("key") apiKey: String
    ): List<DictionaryResponse>
}

@Serializable
data class DictionaryResponse(
    @SerialName("shortdef") val shortdef: List<String>?
)
