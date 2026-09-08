package com.flatcode.simplecomposeapps.news.model

import com.flatcode.simplecomposeapps.utils.DATA
import kotlinx.serialization.Serializable

@Serializable
data class Source(
    var id: String = DATA.EMPTY,
    var name: String = DATA.EMPTY
)