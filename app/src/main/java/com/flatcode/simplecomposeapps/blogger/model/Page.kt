package com.flatcode.simplecomposeapps.blogger.model

import kotlinx.serialization.Serializable

@Serializable
data class Page(
    val author: Author? = null,
    val content: String? = null,
    val id: String? = null,
    val published: String? = null,
    val selfLink: String? = null,
    val title: String? = null,
    val updated: String? = null,
    val url: String? = null
) {
    val authorName: String? get() = author?.displayName
}
