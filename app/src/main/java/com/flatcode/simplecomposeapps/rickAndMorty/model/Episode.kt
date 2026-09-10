package com.flatcode.simplecomposeapps.rickAndMorty.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "episodes")
@Serializable
data class Episode(
    @PrimaryKey val id: Int,
    val name: String,
    @SerialName("air_date") val airDate: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)