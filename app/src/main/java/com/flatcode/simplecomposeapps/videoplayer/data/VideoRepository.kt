package com.flatcode.simplecomposeapps.videoplayer.data

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import com.flatcode.simplecomposeapps.utils.formatDuration
import com.flatcode.simplecomposeapps.utils.formatSize
import com.flatcode.simplecomposeapps.videoplayer.model.VideoFiles
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class VideoRepository(
    private val context: Context,
    private val videoDao: VideoDao
) {

    suspend fun syncWithRoom() = withContext(Dispatchers.IO) {
        val mediaStoreVideos = getVideosFromMediaStore()
        val existingVideos = videoDao.getAllVideos().first()
        val positionMap = existingVideos.associate { it.videoId to it.lastPosition }
        
        val videoEntities = mediaStoreVideos.map {
            VideoEntity(
                videoId = it.id ?: "",
                title = it.title ?: "",
                path = it.path ?: "",
                uriString = it.uriString ?: "",
                fileName = it.fileName ?: "",
                dateAdded = it.dateAdded ?: "",
                bucketName = it.bucketName ?: "Internal Storage",
                size = it.size?.toLongOrNull() ?: 0L,
                sizeReadable = it.size?.toLongOrNull()?.formatSize() ?: "0 B",
                duration = it.duration?.toLongOrNull() ?: 0L,
                durationReadable = it.duration?.toLongOrNull()?.formatDuration() ?: "0:00",
                lastPosition = positionMap[it.id] ?: 0L
            )
        }

        val folders = mediaStoreVideos.groupBy { it.bucketName ?: "Internal Storage" }
            .map { (name, videos) ->
                FolderEntity(
                    name = name,
                    path = videos.firstOrNull()?.path?.substringBeforeLast('/', "") ?: "",
                    videoCount = videos.size
                )
            }

        videoDao.replaceAllVideos(videoEntities)
        videoDao.replaceAllFolders(folders)
    }

    private suspend fun getVideosFromMediaStore(): List<VideoFiles> = withContext(Dispatchers.IO) {
        val tempVideoFiles = mutableListOf<VideoFiles>()
        val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.TITLE,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.DATE_ADDED,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
        )

        context.contentResolver.query(
            uri, projection, null, null, "${MediaStore.Video.Media.DATE_ADDED} DESC"
        )?.use { cursor ->
            val idIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val dataIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            val titleIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE)
            val sizeIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)
            val dateAddedIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED)
            val durationIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
            val displayNameIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val bucketIndex =
                cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idIndex)
                val path = cursor.getString(dataIndex)
                val title = cursor.getString(titleIndex)
                val size = cursor.getString(sizeIndex)
                val dateAdded = cursor.getString(dateAddedIndex)
                val duration = cursor.getString(durationIndex)
                val fileName = cursor.getString(displayNameIndex)
                val bucketName = cursor.getString(bucketIndex) ?: "Internal Storage"

                val contentUri =
                    ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)
                        .toString()

                val videoFile = VideoFiles(
                    id.toString(),
                    path,
                    contentUri,
                    title,
                    fileName,
                    size,
                    dateAdded,
                    duration,
                    bucketName
                )
                tempVideoFiles.add(videoFile)
            }
        }
        tempVideoFiles
    }
}