package com.flatcode.simplecomposeapps.main.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "main_settings")
data class MainSettingsEntity(
    @PrimaryKey val key: String, val count: Int
)