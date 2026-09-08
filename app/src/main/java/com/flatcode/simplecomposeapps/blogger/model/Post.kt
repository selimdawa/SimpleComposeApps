package com.flatcode.simplecomposeapps.blogger.model

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    var authorName: String? = null,
    var content: String? = null,
    var id: String? = null,
    var published: String? = null,
    var selfLink: String? = null,
    var title: String? = null,
    var updated: String? = null,
    var url: String? = null
)
