package com.flatcode.simplecomposeapps.news.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_headlines")
data class NewsEntity(
    @PrimaryKey val title: String,
    val author: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String,
    val sourceName: String,
    @ColumnInfo(defaultValue = "") val category: String = "",
    @ColumnInfo(defaultValue = "0") val isCache: Boolean = false
)