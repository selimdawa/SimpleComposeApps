package com.flatcode.simplecomposeapps.videoplayer.viewmodel

import android.app.Application
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.videoplayer.data.VideoRepository
import com.flatcode.simplecomposeapps.videoplayer.model.Folder
import com.flatcode.simplecomposeapps.videoplayer.model.VideoFiles
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class VideoUiState(
    val videoFiles: List<VideoFiles> = emptyList(),
    val folderList: List<Folder> = emptyList(),
    val isRefreshing: Boolean = false
)

class VideoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = VideoRepository(application)

    private val _uiState = MutableStateFlow(VideoUiState())
    val uiState: StateFlow<VideoUiState> = _uiState.asStateFlow()

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
    }

    fun loadVideos(isInternalUpdate: Boolean = false) {
        viewModelScope.launch {
            if (!isInternalUpdate) {
                _uiState.update { it.copy(isRefreshing = true) }
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

            _uiState.update { 
                it.copy(
                    videoFiles = allVideos,
                    folderList = folders,
                    isRefreshing = false
                )
            }
        }
    }

    override fun onCleared() {
        getApplication<Application>().contentResolver.unregisterContentObserver(contentObserver)
    }
}