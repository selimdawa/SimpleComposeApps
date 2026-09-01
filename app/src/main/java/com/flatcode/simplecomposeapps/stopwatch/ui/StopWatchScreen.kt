package com.flatcode.simplecomposeapps.stopwatch.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.flatcode.simplecomposeapps.ui.ToolbarContent
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.ui.theme.rememberAttributeColor
import io.selimdawa.multicolors.MultiColorManager

@Composable
fun StopWatchScreen(
    viewModel: StopWatchViewModel,
    onBack: () -> Unit
) {
    val themeId by MultiColorManager.currentThemeId.collectAsState()
    val colorError = rememberAttributeColor("colorError", Color.Red, themeId)

    val lastTimeValue by viewModel.lastTime

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ToolbarContent(
                title = Strings.STOP_WATCH,
                hasBack = false,
                onBackClick = onBack
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            StopWatchContent(viewModel = viewModel)

            Spacer(modifier = Modifier.height(50.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = Strings.LAST_TIME_LABEL,
                    color = colorError,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = lastTimeValue,
                    color = colorError,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}