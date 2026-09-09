package com.flatcode.simplecomposeapps.news2.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.flatcode.simplecomposeapps.news2.base.IBaseDiffModel
import kotlinx.serialization.Serializable

@Entity(tableName = "top_articles_news")
@Serializable
data class TopArticlesNewsItem(
    val title: String,
    val urlToImage: String?,
    val url: String,
    @PrimaryKey override val id: String = url
) : IBaseDiffModel<String>