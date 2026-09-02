package com.flatcode.simplecomposeapps.stopwatch.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flatcode.simplecomposeapps.stopwatch.StopWatchViewModel
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.rememberAttributeColor
import io.selimdawa.multicolors.MultiColorManager

@Composable
fun StopWatchContent(
    viewModel: StopWatchViewModel
) {
    val themeId by MultiColorManager.currentThemeId.collectAsState()
    val mcTick = rememberAttributeColor("mc_track", Color.LightGray, themeId)

    val timeDisplay by viewModel.timeDisplay
    val isRunning by viewModel.isRunning

    Card(
        modifier = Modifier.size(300.dp),
        shape = CircleShape,
        colors = CardDefaults.cardColors(containerColor = mcTick),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = timeDisplay,
                color = Color.White,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row {
                IconButton(onClick = { viewModel.startOrPause() }) {
                    Icon(
                        imageVector = if (isRunning) AppIcons.Pause else AppIcons.Play,
                        contentDescription = if (isRunning) "Pause" else "Start",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(48.dp)
                    )
                }

                if (!isRunning) {
                    IconButton(onClick = { viewModel.stop() }) {
                        Icon(
                            imageVector = AppIcons.Stop,
                            contentDescription = "Stop",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }
        }
    }
}