package com.flatcode.simplecomposeapps.randomimagegenerating.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.flatcode.simplecomposeapps.randomimagegenerating.RandomImageGeneratingViewModel
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.ToolbarContent
import com.flatcode.simplecomposeapps.ui.theme.Strings

@Composable
fun RandomImageGeneratingScreen(
    viewModel: RandomImageGeneratingViewModel,
    onNavigateToInfo: () -> Unit,
    onDownload: (String) -> Unit
) {
    val isLoading by viewModel.isLoading
    val imageUrl by viewModel.imageUrl

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = AppIcons.Blur),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                ToolbarContent(
                    title = Strings.RANDOM_IMAGE_GENERATING,
                    leftIcon = null,
                    includeStatusBarsPadding = true
                )
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                RandomImageGeneratingContent(
                    viewModel = viewModel,
                    onNavigateToInfo = onNavigateToInfo,
                    onDownload = onDownload
                )

                if (isLoading && imageUrl.isEmpty()) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
