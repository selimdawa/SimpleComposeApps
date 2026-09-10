package com.flatcode.simplecomposeapps.news.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.flatcode.simplecomposeapps.utils.DATA
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "news_headlines")
data class NewsHeadlines(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @Embedded(prefix = "source_")
    var source: Source? = null,
    var author: String? = null,
    var title: String? = null,
    var description: String? = null,
    var url: String? = null,
    var urlToImage: String? = null,
    var publishedAt: String? = null,
    var content: String? = null,
    var category: String = DATA.EMPTY
)