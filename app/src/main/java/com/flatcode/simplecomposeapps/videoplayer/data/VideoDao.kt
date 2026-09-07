package com.flatcode.simplecomposeapps.videoplayer.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoDao {
    @Query("SELECT * FROM videos")
    fun getAllVideos(): Flow<List<VideoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideo(video: VideoEntity)

    @Query("UPDATE videos SET lastPosition = :position WHERE videoId = :videoId")
    suspend fun updatePosition(videoId: String, position: Long)

    @Delete
    suspend fun deleteVideo(video: VideoEntity)

    @Query("SELECT * FROM video_settings WHERE id = 1")
    fun getSettings(): Flow<VideoSettingsEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSettings(settings: VideoSettingsEntity)
}