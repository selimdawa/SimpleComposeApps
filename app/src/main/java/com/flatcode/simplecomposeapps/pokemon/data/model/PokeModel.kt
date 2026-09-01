package com.flatcode.simplecomposeapps.pokemon.data.model

data class PokeModel(
    val results: List<PokeResult>
)

data class PokeResult(
    val name: String,
    val url: String
)