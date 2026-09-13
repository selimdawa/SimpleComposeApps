package com.flatcode.simplecomposeapps.blogger.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "blogger_comments")
data class BloggerCommentEntity(
    @PrimaryKey val id: String,
    val postId: String,
    val name: String?,
    val profileImage: String?,
    val published: String?,
    val comment: String?
)
