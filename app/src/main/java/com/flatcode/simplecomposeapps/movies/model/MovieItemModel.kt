package com.flatcode.simplecomposeapps.movies.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "movies_table")
@Serializable
data class MovieItemModel(
    @PrimaryKey val id: Int,
    val overview: String? = null,
    @SerialName("poster_path") @ColumnInfo(name = "poster_path") val posterPath: String? = null,
    @SerialName("release_date") @ColumnInfo(name = "release_date") val releaseDate: String? = null,
    val title: String? = null,
    val isFavorite: Boolean = false
)