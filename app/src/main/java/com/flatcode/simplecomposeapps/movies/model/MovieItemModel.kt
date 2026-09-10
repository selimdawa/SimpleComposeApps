package com.flatcode.simplecomposeapps.movies.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "movies_table")
@Serializable
data class MovieItemModel(
    @PrimaryKey val id: Int,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val title: String
)