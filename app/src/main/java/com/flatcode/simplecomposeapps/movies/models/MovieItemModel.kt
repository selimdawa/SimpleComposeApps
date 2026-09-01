package com.flatcode.simplecomposeapps.movies.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "movies_table")
data class MovieItemModel(
    @PrimaryKey val id: Int,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val title: String
) : Serializable