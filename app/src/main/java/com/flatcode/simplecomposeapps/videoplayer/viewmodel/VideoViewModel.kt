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
import com.flatcode.simplecomposeapps.videoplayer.data.FolderEntity
import com.flatcode.simplecomposeapps.videoplayer.data.VideoSettingsEntity
import com.flatcode.simplecomposeapps.videoplayer.data.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
    private val videoDao: VideoDao,
    private val repository: VideoRepository
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(VideoUiState())
    val uiState: StateFlow<VideoUiState> = _uiState

    private val contentObserver = object : ContentObserver(Handler(Looper.getMainLooper())) {
        override fun onChange(selfChange: Boolean, uri: Uri?) {
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
                videoDao.getAllVideos(),
                videoDao.getAllFolders(),
                videoDao.getSettings()
            ) { videos, folders, settings ->
                VideoUiState(
                    videoFiles = videos,
                    folderList = folders,
                    isLoading = false,
                    isRefreshing = false,
                    lastVideoId = settings?.lastVideoId,
                    lastPosition = settings?.lastPosition ?: 0L
                )
            }.collect { newState ->
                _uiState.value = newState
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
                _uiState.update { it.copy(isRefreshing = true) }
            }
            repository.syncWithRoom()
        }
    }

    override fun onCleared() {
        getApplication<Application>().contentResolver.unregisterContentObserver(contentObserver)
    }
}