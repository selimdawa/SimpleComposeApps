package com.flatcode.simplecomposeapps.videoplayer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.rememberAttributeColor
import com.flatcode.simplecomposeapps.videoplayer.model.Folder
import io.selimdawa.multicolors.MultiColorManager

@Composable
fun FolderItem(folder: Folder, onClick: () -> Unit) {
    val themeId by MultiColorManager.currentThemeId.collectAsState()
    val mcTrack = rememberAttributeColor("mc_track", Color.White, themeId)
    val colorError = rememberAttributeColor("colorError", Color.Red, themeId)

    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }
        .padding(vertical = 5.dp)) {
        Icon(
            imageVector = AppIcons.Folder,
            contentDescription = null,
            tint = mcTrack,
            modifier = Modifier
                .padding(start = 10.dp)
                .size(80.dp)
                .align(Alignment.CenterStart)
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = 50.dp, y = (-10).dp)
                .background(Color(0x99000000), RoundedCornerShape(5.dp))
                .padding(horizontal = 5.dp)
                .defaultMinSize(minWidth = 8.dp), contentAlignment = Alignment.Center
        ) {
            Text(
                text = folder.videoCount.toString(), color = Color.White, fontSize = 12.sp
            )
        }

        Text(
            text = folder.name,
            color = colorError,
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 100.dp)
        )
    }
}