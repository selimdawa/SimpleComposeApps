package com.flatcode.simplecomposeapps.rickAndMorty.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "locations")
@Serializable
data class Location(
    @PrimaryKey val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String
)