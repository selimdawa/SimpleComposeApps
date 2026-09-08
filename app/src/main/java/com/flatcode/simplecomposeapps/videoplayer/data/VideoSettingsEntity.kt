package com.flatcode.simplecomposeapps.videoplayer.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video_settings")
data class VideoSettingsEntity(
    @PrimaryKey val id: Int = 1,
    val lastVideoId: String?,
    val lastPosition: Long
)
