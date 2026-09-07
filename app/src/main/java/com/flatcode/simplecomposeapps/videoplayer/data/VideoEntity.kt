package com.flatcode.simplecomposeapps.videoplayer.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "videos")
data class VideoEntity(
    @PrimaryKey val videoId: String,
    val title: String,
    val path: String,
    val uriString: String,
    val fileName: String,
    val dateAdded: String,
    val bucketName: String,
    val size: Long,
    val sizeReadable: String,
    val duration: Long,
    val durationReadable: String,
    val lastPosition: Long
)