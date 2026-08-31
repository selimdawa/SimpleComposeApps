package com.flatcode.simplecomposeapps.videoplayer.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.flatcode.simplecomposeapps.ui.theme.SimpleComposeAppsTheme
import com.flatcode.simplecomposeapps.utils.launchActivity
import com.flatcode.simplecomposeapps.videoplayer.model.VideoData
import com.flatcode.simplecomposeapps.videoplayer.ui.VideoFolderScreen
import com.flatcode.simplecomposeapps.videoplayer.viewmodel.VideoViewModel

class VideoFolderActivity : ComponentActivity() {

    private val viewModel: VideoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val folderName = intent.getStringExtra("folderName") ?: ""

        setContent {
            SimpleComposeAppsTheme {
                VideoFolderScreen(
                    viewModel = viewModel,
                    folderPath = folderName,
                    onBack = { finish() },
                    onVideoClick = { position, filteredVideos ->
                        VideoData.folderVideoFile = ArrayList(filteredVideos)
                        launchActivity<PlayerActivity> {
                            putExtra("position", position)
                            putExtra("sender", "FolderIsSending")
                        }
                    }
                )
            }
        }
    }
}
