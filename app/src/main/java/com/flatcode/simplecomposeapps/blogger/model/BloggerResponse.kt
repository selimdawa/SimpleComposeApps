package com.flatcode.simplecomposeapps.blogger.model

import kotlinx.serialization.Serializable

@Serializable
data class BloggerResponse<T>(
    val items: List<T>? = null,
    val nextPageToken: String? = null
)

@Serializable
data class Author(
    val displayName: String? = null,
    val image: AuthorImage? = null
)

@Serializable
data class AuthorImage(
    val url: String? = null
)

@Serializable
data class CommentResponse(
    val items: List<CommentItem>? = null
)

@Serializable
data class CommentItem(
    val id: String? = null,
    val published: String? = null,
    val content: String? = null,
    val author: Author? = null
)