package com.flatcode.simplecomposeapps.blogger.model

import kotlinx.serialization.Serializable

@Serializable
data class Label(
    var label: String? = null
)