package com.flatcode.simplecomposeapps.stopwatch.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stopwatch_settings")
data class StopWatchEntity(
    @PrimaryKey val id: Int = 1,
    val lastTime: String?
)