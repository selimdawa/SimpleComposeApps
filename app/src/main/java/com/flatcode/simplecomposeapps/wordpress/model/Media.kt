package com.flatcode.simplecomposeapps.wordpress.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Media {
    @SerialName("guid")
    var guid: Rendered? = null
}