package com.flatcode.simplecomposeapps.videoplayer.viewmodel

import android.app.Application
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.videoplayer.data.VideoDao
import com.flatcode.simplecomposeapps.videoplayer.data.VideoEntity
import com.flatcode.simplecomposeapps.videoplayer.data.VideoSettingsEntity
import com.flatcode.simplecomposeapps.videoplayer.data.VideoRepository
import com.flatcode.simplecomposeapps.videoplayer.model.Folder
import com.flatcode.simplecomposeapps.videoplayer.model.VideoFiles
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class VideoUiState(
    val videoFiles: List<VideoFiles> = emptyList(),
    val folderList: List<Folder> = emptyList(),
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val lastVideoId: String? = null,
    val lastPosition: Long = 0L
)

@HiltViewModel
class VideoViewModel @Inject constructor(
    application: Application,
    private val videoDao: VideoDao
) : AndroidViewModel(application) {

    private val repository = VideoRepository(application)

    val uiState: StateFlow<VideoUiState>
        field = MutableStateFlow(VideoUiState())

    private val contentObserver = object : ContentObserver(Handler(Looper.getMainLooper())) {
        override fun onChange(selfChange: Boolean, uri: Uri?) {
            loadVideos(true)
        }
    }

    init {
        application.contentResolver.registerContentObserver(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI, true, contentObserver
        )
        loadVideos()
        observePlayback()
    }

    private fun observePlayback() {
        viewModelScope.launch {
            videoDao.getSettings().collectLatest { settings ->
                uiState.update { 
                    it.copy(
                        lastVideoId = settings?.lastVideoId,
                        lastPosition = settings?.lastPosition ?: 0L
                    )
                }
            }
        }
    }

    fun savePlayback(videoId: String, position: Long) {
        viewModelScope.launch {
            videoDao.saveSettings(VideoSettingsEntity(lastVideoId = videoId, lastPosition = position))
            videoDao.updatePosition(videoId, position)
        }
    }

    fun loadVideos(isInternalUpdate: Boolean = false) {
        viewModelScope.launch {
            if (!isInternalUpdate) {
                uiState.update { it.copy(isRefreshing = true, isLoading = true) }
                repository.refreshMediaStore()
            }
            val allVideos = repository.getAllVideos()

            // Extract folders using bucketName and count videos
            val folders = allVideos.groupBy { video ->
                video.bucketName ?: "Internal Storage"
            }.map { (name, videos) ->
                Folder(
                    name = name,
                    path = videos.firstOrNull()?.path?.substringBeforeLast('/', "") ?: "",
                    videoCount = videos.size
                )
            }

            uiState.update { 
                it.copy(
                    videoFiles = allVideos,
                    folderList = folders,
                    isRefreshing = false,
                    isLoading = false
                )
            }
        }
    }

    override fun onCleared() {
        getApplication<Application>().contentResolver.unregisterContentObserver(contentObserver)
    }
}