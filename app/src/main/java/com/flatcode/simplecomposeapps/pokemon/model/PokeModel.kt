package com.flatcode.simplecomposeapps.pokemon.model

import kotlinx.serialization.Serializable

@Serializable
data class PokeModel(
    val results: List<PokeResult>
)

@Serializable
data class PokeResult(
    val name: String,
    val url: String
)