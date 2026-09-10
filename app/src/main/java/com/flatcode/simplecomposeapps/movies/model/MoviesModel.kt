package com.flatcode.simplecomposeapps.movies.model

import kotlinx.serialization.Serializable

@Serializable
data class MoviesModel(
    val results: List<MovieItemModel>
)