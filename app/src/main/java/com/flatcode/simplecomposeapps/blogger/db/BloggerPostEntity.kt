package com.flatcode.simplecomposeapps.blogger.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.flatcode.simplecomposeapps.blogger.model.Author

@Entity(tableName = "blogger_posts")
data class BloggerPostEntity(
    @PrimaryKey val id: String,
    val author: Author?,
    val content: String?,
    val published: String?,
    val selfLink: String?,
    val title: String?,
    val updated: String?,
    val url: String?,
    val labels: List<String>?
)
