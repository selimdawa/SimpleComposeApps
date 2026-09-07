package com.flatcode.simplecomposeapps.videoplayer.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "folders")
data class FolderEntity(
    @PrimaryKey val name: String,
    val path: String,
    val videoCount: Int
)