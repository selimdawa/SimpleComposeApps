package com.flatcode.simplecomposeapps.videoplayer.viewmodel

import android.app.Application
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.videoplayer.data.FolderEntity
import com.flatcode.simplecomposeapps.videoplayer.data.VideoEntity
import com.flatcode.simplecomposeapps.videoplayer.data.VideoRepository
import com.flatcode.simplecomposeapps.videoplayer.data.VideoSettingsEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class VideoUiState(
    val videoFiles: List<VideoEntity> = emptyList(),
    val folderList: List<FolderEntity> = emptyList(),
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val lastVideoId: String? = null,
    val lastPosition: Long = 0L
)

@HiltViewModel
class VideoViewModel @Inject constructor(
    application: Application,
    private val repository: VideoRepository
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(VideoUiState())
    val uiState: StateFlow<VideoUiState> = _uiState.asStateFlow()

    private val contentObserver = object : ContentObserver(Handler(Looper.getMainLooper())) {
        override fun onChange(selfChange: Boolean, uri: Uri?) {
            Timber.d("ContentObserver detected changes, reloading videos")
            loadVideos(true)
        }
    }

    init {
        application.contentResolver.registerContentObserver(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI, true, contentObserver
        )
        observeData()
        loadVideos()
    }

    private fun observeData() {
        viewModelScope.launch {
            combine(
                repository.getAllVideos(), repository.getAllFolders(), repository.getSettings()
            ) { videos, folders, settings ->
                Triple(videos, folders, settings)
            }.collect { (videos, folders, settings) ->
                val currentState = _uiState.value
                _uiState.value = currentState.copy(
                    videoFiles = videos,
                    folderList = folders,
                    isLoading = if (videos.isNotEmpty() || folders.isNotEmpty()) false else currentState.isLoading,
                    lastVideoId = settings?.lastVideoId,
                    lastPosition = settings?.lastPosition ?: 0L
                )
            }
        }
    }

    fun loadVideos(isInternalUpdate: Boolean = false) {
        viewModelScope.launch {
            val currentState = _uiState.value
            if (!isInternalUpdate) {
                _uiState.value = currentState.copy(isRefreshing = true)
            }
            repository.syncWithRoom()
            val updatedState = _uiState.value
            _uiState.value = updatedState.copy(isLoading = false, isRefreshing = false)
        }
    }

    fun updatePosition(videoId: String, position: Long) {
        viewModelScope.launch {
            repository.updatePosition(videoId, position)
        }
    }

    fun saveSettings(videoId: String, position: Long) {
        viewModelScope.launch {
            repository.saveSettings(VideoSettingsEntity(lastVideoId = videoId, lastPosition = position))
        }
    }

    override fun onCleared() {
        getApplication<Application>().contentResolver.unregisterContentObserver(contentObserver)
    }
}