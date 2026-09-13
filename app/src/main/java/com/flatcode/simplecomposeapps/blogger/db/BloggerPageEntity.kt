package com.flatcode.simplecomposeapps.blogger.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.flatcode.simplecomposeapps.blogger.model.Author

@Entity(tableName = "blogger_pages")
data class BloggerPageEntity(
    @PrimaryKey val id: String,
    val author: Author?,
    val content: String?,
    val published: String?,
    val selfLink: String?,
    val title: String?,
    val updated: String?,
    val url: String?
)
