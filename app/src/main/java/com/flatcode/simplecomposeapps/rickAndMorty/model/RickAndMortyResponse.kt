package com.flatcode.simplecomposeapps.rickAndMorty.model

import kotlinx.serialization.Serializable

@Serializable
data class RickAndMortyResponse<T>(
    val info: Info,
    val results: List<T>
)

@Serializable
data class Info(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)