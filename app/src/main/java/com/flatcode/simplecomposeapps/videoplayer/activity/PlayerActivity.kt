package com.flatcode.simplecomposeapps.videoplayer.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.videoplayer.model.VideoData
import com.flatcode.simplecomposeapps.videoplayer.model.VideoFiles
import com.flatcode.simplecomposeapps.videoplayer.ui.PlayerScreen

class PlayerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val position = intent.getIntExtra("position", -1)
        val sender = intent.getStringExtra("sender")

        val myFiles: ArrayList<VideoFiles?>? = if (sender == "FolderIsSending") VideoData.folderVideoFile else VideoData.videoFile

        if ((myFiles.isNullOrEmpty()) || (position == -1)) {
            Toast.makeText(this, Strings.NO_DATA_FOUND, Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setContent {
            PlayerScreen(
                videos = myFiles,
                initialPosition = position,
                onBack = { finish() }
            )
        }
    }
}
