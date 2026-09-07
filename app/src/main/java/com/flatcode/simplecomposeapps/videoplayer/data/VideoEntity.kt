package com.flatcode.simplecomposeapps.videoplayer.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "videos")
data class VideoEntity(
    @PrimaryKey val videoId: String,
    val title: String,
    val path: String,
    val duration: Long,
    val size: Long,
    val lastPosition: Long
)