package com.flatcode.simplecomposeapps.countries.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country_settings")
data class CountrySettings(
    @PrimaryKey val id: Int = 1,
    val refreshTime: Long
)