package com.flatcode.simplecomposeapps.videoplayer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.flatcode.simplecomposeapps.ui.theme.ImageProfile
import com.flatcode.simplecomposeapps.ui.theme.rememberAttributeColor
import com.flatcode.simplecomposeapps.utils.formatDuration
import com.flatcode.simplecomposeapps.videoplayer.model.VideoFiles
import io.selimdawa.multicolors.MultiColorManager

@Composable
fun VideoItem(video: VideoFiles, onClick: () -> Unit) {
    val themeId by MultiColorManager.currentThemeId.collectAsState()
    val colorError = rememberAttributeColor("colorError", Color.Red, themeId)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier.size(width = 112.dp, height = 62.dp),
            shape = RoundedCornerShape(5.dp),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = video.uriString,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(ImageProfile),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .background(Color(0x99000000), RoundedCornerShape(5.dp))
                        .padding(horizontal = 5.dp)
                ) {
                    val durationMs = video.duration?.toLongOrNull() ?: 0L
                    Text(
                        text = durationMs.formatDuration(), color = Color.White, fontSize = 12.sp
                    )
                }
            }
        }

        Text(
            text = video.title ?: "Unknown",
            color = colorError,
            fontSize = 14.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp)
                .weight(1f)
        )
    }
}