package com.flatcode.simplecomposeapps.videoplayer.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.videoplayer.model.VideoFiles
import com.flatcode.simplecomposeapps.videoplayer.viewmodel.VideoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoFolderScreen(
    viewModel: VideoViewModel,
    folderPath: String,
    onBack: () -> Unit,
    onVideoClick: (Int, List<VideoFiles>) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    val filteredVideos = remember(uiState.videoFiles, folderPath) {
        uiState.videoFiles.filter { it.path?.substringBeforeLast('/', "") == folderPath }
    }

    Scaffold(
        containerColor = COLOR_ON_BACKGROUND
    ) { paddingValues ->
        PullToRefreshBox(
            isRefreshing = uiState.isRefreshing,
            onRefresh = { viewModel.loadVideos() },
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(filteredVideos.indices.toList()) { index ->
                    VideoItem(video = filteredVideos[index], onClick = {
                        onVideoClick(index, filteredVideos)
                    })
                }
            }
        }
    }
}
