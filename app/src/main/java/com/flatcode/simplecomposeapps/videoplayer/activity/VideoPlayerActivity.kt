package com.flatcode.simplecomposeapps.videoplayer.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.flatcode.simplecomposeapps.ui.theme.SimpleComposeAppsTheme
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.videoplayer.model.VideoData
import com.flatcode.simplecomposeapps.videoplayer.ui.VideoPlayerScreen
import com.flatcode.simplecomposeapps.videoplayer.viewmodel.VideoViewModel

class VideoPlayerActivity : ComponentActivity() {

    private val viewModel: VideoViewModel by viewModels()

    private val videoPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.loadVideos()
        } else {
            Toast.makeText(this, Strings.PERMISSION_DENIED, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        checkAndRequestPermissions()

        setContent {
            SimpleComposeAppsTheme {
                VideoPlayerScreen(
                    viewModel = viewModel,
                    onBack = { finish() },
                    onVideoClick = { position ->
                        VideoData.videoFile = ArrayList(viewModel.uiState.value.videoFiles)
                        // Navigate to PlayerActivity
                        val intent = Intent(this, PlayerActivity::class.java).apply {
                            putExtra("position", position)
                            putExtra("sender", "FilesIsSending")
                        }
                        startActivity(intent)
                    },
                    onFolderClick = { folderPath ->
                        val intent = Intent(this, VideoFolderActivity::class.java).apply {
                            putExtra("folderName", folderPath)
                        }
                        startActivity(intent)
                    }
                )
            }
        }
    }

    private fun checkAndRequestPermissions() {
        val videoPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_VIDEO
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        if (ContextCompat.checkSelfPermission(this, videoPermission) != PackageManager.PERMISSION_GRANTED) {
            videoPermissionLauncher.launch(videoPermission)
        } else {
            viewModel.loadVideos()
        }
    }
}
