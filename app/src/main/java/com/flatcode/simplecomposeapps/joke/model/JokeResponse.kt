package com.flatcode.simplecomposeapps.joke.model

import kotlinx.serialization.Serializable

@Serializable
data class JokeResponse(
    val error: Boolean,
    val jokes: List<Joke>? = null,
    val message: String? = null
)
