package com.flatcode.simplecomposeapps.wordpress.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Rendered(
    @SerialName("rendered")
    val rendered: String? = ""
) {
    override fun toString(): String {
        return rendered ?: ""
    }
}
