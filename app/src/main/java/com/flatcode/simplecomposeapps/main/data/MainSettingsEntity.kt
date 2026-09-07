package com.flatcode.simplecomposeapps.main.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "main_settings")
data class MainSettingsEntity(
    @PrimaryKey val key: String, // e.g. "title_type"
    val count: Int
)