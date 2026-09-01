package com.flatcode.simplecomposeapps.news2.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.flatcode.simplecomposeapps.news2.base.IBaseDiffModel

@Entity(tableName = "top_articles_news")
data class TopArticlesNewsItem(
    @PrimaryKey override val id: String,
    val title: String,
    val urlToImage: String?,
    val url: String
) : IBaseDiffModel<String>