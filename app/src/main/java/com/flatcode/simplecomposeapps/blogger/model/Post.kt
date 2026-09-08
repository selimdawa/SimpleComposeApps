package com.flatcode.simplecomposeapps.blogger.model

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val author: Author? = null,
    val content: String? = null,
    val id: String? = null,
    val published: String? = null,
    val selfLink: String? = null,
    val title: String? = null,
    val updated: String? = null,
    val url: String? = null,
    val labels: List<String>? = null
) {
    val authorName: String? get() = author?.displayName
}
