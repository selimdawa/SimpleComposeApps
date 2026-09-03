package com.flatcode.simplecomposeapps.videoplayer.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.MC_TRACK
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.videoplayer.viewmodel.VideoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoPlayerScreen(
    viewModel: VideoViewModel,
    onBack: () -> Unit,
    onVideoClick: (Int) -> Unit,
    onFolderClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = COLOR_ON_BACKGROUND) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = {
                        Icon(
                            imageVector = AppIcons.Folder,
                            contentDescription = Strings.FOLDERS
                        )
                    },
                    label = { Text(Strings.FOLDERS) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MC_TRACK,
                        selectedTextColor = MC_TRACK,
                        unselectedIconColor = MC_TRACK.copy(alpha = 0.6f),
                        unselectedTextColor = MC_TRACK.copy(alpha = 0.6f),
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = {
                        Icon(
                            imageVector = AppIcons.Video,
                            contentDescription = Strings.FILES
                        )
                    },
                    label = { Text(Strings.FILES) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MC_TRACK,
                        selectedTextColor = MC_TRACK,
                        unselectedIconColor = MC_TRACK.copy(alpha = 0.6f),
                        unselectedTextColor = MC_TRACK.copy(alpha = 0.6f),
                        indicatorColor = Color.Transparent
                    )
                )
            }
        },
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
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                if (selectedTab == 0) {
                    items(uiState.folderList) { folder ->
                        FolderItem(
                            folder = folder,
                            onClick = { folder.path?.let { onFolderClick(it) } })
                    }
                } else {
                    items(uiState.videoFiles.indices.toList()) { index ->
                        VideoItem(
                            video = uiState.videoFiles[index],
                            onClick = { onVideoClick(index) })
                    }
                }
            }
        }
    }
}