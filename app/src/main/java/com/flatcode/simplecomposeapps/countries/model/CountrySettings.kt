package com.flatcode.simplecomposeapps.countries.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "country_settings")
@Serializable
data class CountrySettings(
    @PrimaryKey val id: Int = 1,
    val refreshTime: Long
)