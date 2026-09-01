package com.flatcode.simplecomposeapps.news2.models

import com.google.gson.annotations.SerializedName

data class NewsResponse<T>(
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("articles") val articles: List<T>
)