package com.flatcode.simplecomposeapps.rickAndMorty.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.flatcode.simplecomposeapps.rickAndMorty.ui.base.IBaseDiffModel
import com.google.gson.annotations.SerializedName

data class RickAndMortyResponse<T>(
    val info: Info,
    val results: List<T>
)

data class Info(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)

@Entity(tableName = "characters")
data class Character(
    @PrimaryKey override val id: Int,
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
) : IBaseDiffModel<Int>

@Entity(tableName = "locations")
data class Location(
    @PrimaryKey override val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String
) : IBaseDiffModel<Int>

@Entity(tableName = "episodes")
data class Episode(
    @PrimaryKey override val id: Int,
    val name: String,
    @SerializedName("air_date") val airDate: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
) : IBaseDiffModel<Int>

data class LocationShort(
    val name: String,
    val url: String
)