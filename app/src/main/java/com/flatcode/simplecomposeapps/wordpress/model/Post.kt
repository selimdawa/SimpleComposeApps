package com.flatcode.simplecomposeapps.wordpress.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Post(
    @SerialName("id")
    val id: Int = 0,

    @SerialName("featured_media")
    val featuredMedia: Int = 0,

    @SerialName("title")
    val title: Rendered? = null,

    @SerialName("excerpt")
    val excerpt: Rendered? = null,

    @SerialName("content")
    val content: Rendered? = null,

    val sqLiteId: Int = 0,
    val wpPostId: Int = 0,
    val wpTitle: String? = null,
    val wpExcerpt: String? = null,
    val wpContent: String? = null,
    val featuredMediaUrl: String? = null,
    val isFavorite: Boolean = false
)