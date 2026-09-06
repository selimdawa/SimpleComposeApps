package com.flatcode.simplecomposeapps.randomcatsimage.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.flatcode.simplecomposeapps.randomcatsimage.RandomCatsImageViewModel
import com.flatcode.simplecomposeapps.ui.ToolbarContent
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.MC_TRACK
import com.flatcode.simplecomposeapps.utils.DATA

@Composable
fun RandomCatsImageScreen(
    viewModel: RandomCatsImageViewModel, onDownload: (String) -> Unit
) {
    val isLoading by viewModel.isLoading
    val imageUrl by viewModel.imageUrl

    Scaffold(
        modifier = Modifier.fillMaxSize(), topBar = {
            ToolbarContent(
                title = DATA.RANDOM_IMAGE, leftIcon = null, includeStatusBarsPadding = true
            )
        }, containerColor = COLOR_ON_BACKGROUND
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            RandomCatsImageContent(
                viewModel = viewModel, onDownload = onDownload
            )

            if (isLoading && imageUrl.isEmpty()) {
                CircularProgressIndicator(color = MC_TRACK)
            }
        }
    }
}