package com.flatcode.simplecomposeapps.news2.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.flatcode.simplecomposeapps.news2.base.IBaseDiffModel
import kotlinx.serialization.Serializable

@Entity(tableName = "everything_news")
@Serializable
data class EverythingNewsItem(
    @PrimaryKey override val id: String,
    val title: String,
    val url: String
) : IBaseDiffModel<String>
