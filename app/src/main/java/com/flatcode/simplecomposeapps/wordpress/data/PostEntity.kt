package com.flatcode.simplecomposeapps.wordpress.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val wpPostId: Int,
    val wpTitle: String?,
    val wpExcerpt: String?,
    val wpContent: String?,
    val featuredMedia: Int,
    val featuredMediaUrl: String?,
    val isFavorite: Boolean
)