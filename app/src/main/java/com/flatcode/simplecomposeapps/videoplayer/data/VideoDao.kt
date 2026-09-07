package com.flatcode.simplecomposeapps.videoplayer.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoDao {
    @Query("SELECT * FROM videos ORDER BY dateAdded DESC")
    fun getAllVideos(): Flow<List<VideoEntity>>

    @Query("SELECT * FROM videos WHERE bucketName = :folderName ORDER BY dateAdded DESC")
    fun getVideosByFolder(folderName: String): Flow<List<VideoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideos(videos: List<VideoEntity>)

    @Query("DELETE FROM videos")
    suspend fun clearAllVideos()

    @Query("UPDATE videos SET lastPosition = :position WHERE videoId = :videoId")
    suspend fun updatePosition(videoId: String, position: Long)

    @Delete
    suspend fun deleteVideo(video: VideoEntity)

    @Query("SELECT * FROM folders ORDER BY name ASC")
    fun getAllFolders(): Flow<List<FolderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFolders(folders: List<FolderEntity>)

    @Query("DELETE FROM folders")
    suspend fun clearAllFolders()

    @Query("SELECT * FROM video_settings WHERE id = 1")
    fun getSettings(): Flow<VideoSettingsEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSettings(settings: VideoSettingsEntity)
}