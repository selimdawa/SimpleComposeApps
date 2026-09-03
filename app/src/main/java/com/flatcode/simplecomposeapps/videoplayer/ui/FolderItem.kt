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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ERROR
import com.flatcode.simplecomposeapps.ui.theme.MC_TRACK
import com.flatcode.simplecomposeapps.videoplayer.model.Folder

@Composable
fun FolderItem(folder: Folder, onClick: () -> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }
        .padding(vertical = 5.dp)) {
        Icon(
            imageVector = AppIcons.Folder,
            contentDescription = null,
            tint = MC_TRACK,
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
            color = COLOR_ERROR,
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 100.dp)
        )
    }
}