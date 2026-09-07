package com.flatcode.simplecomposeapps.videoplayer.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [VideoEntity::class, VideoSettingsEntity::class, FolderEntity::class],
    version = 2,
    exportSchema = true
)
abstract class VideoDatabase : RoomDatabase() {
    abstract fun videoDao(): VideoDao
}