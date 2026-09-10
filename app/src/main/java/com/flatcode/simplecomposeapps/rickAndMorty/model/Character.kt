package com.flatcode.simplecomposeapps.rickAndMorty.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "characters")
@Serializable
data class Character(
    @PrimaryKey val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: LocationShort,
    val location: LocationShort,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)

@Serializable
data class LocationShort(
    val name: String,
    val url: String
)