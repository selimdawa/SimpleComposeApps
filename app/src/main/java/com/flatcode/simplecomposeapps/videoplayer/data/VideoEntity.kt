package com.flatcode.simplecomposeapps.videoplayer.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "videos")
data class VideoEntity(
    @PrimaryKey val videoId: String,
    val title: String,
    val path: String,
    @ColumnInfo(defaultValue = "") val uriString: String,
    @ColumnInfo(defaultValue = "") val fileName: String,
    @ColumnInfo(defaultValue = "") val dateAdded: String,
    @ColumnInfo(defaultValue = "") val bucketName: String,
    val size: Long,
    @ColumnInfo(defaultValue = "") val sizeReadable: String,
    val duration: Long,
    @ColumnInfo(defaultValue = "") val durationReadable: String,
    val lastPosition: Long
)