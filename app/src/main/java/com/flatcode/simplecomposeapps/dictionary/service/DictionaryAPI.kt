package com.flatcode.simplecomposeapps.dictionary.service

import com.google.gson.annotations.SerializedName
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

data class DictionaryResponse(
    @SerializedName("shortdef") val shortdef: List<String>?
)