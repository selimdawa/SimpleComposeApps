package com.flatcode.simplecomposeapps.news.model

import kotlinx.serialization.Serializable

@Serializable
data class Source(
    var id: String? = null,
    var name: String? = null
)