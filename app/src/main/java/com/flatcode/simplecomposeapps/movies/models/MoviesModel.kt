package com.flatcode.simplecomposeapps.movies.models

import kotlinx.serialization.Serializable

@Serializable
data class MoviesModel(
    val results: List<MovieItemModel>
)