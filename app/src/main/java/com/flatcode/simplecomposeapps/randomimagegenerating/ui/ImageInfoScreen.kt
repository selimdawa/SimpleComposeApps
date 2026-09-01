package com.flatcode.simplecomposeapps.randomimagegenerating.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.flatcode.simplecomposeapps.randomimagegenerating.ImageInfoViewModel
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.ToolbarContent
import com.flatcode.simplecomposeapps.ui.theme.Strings

@Composable
fun ImageInfoScreen(
    viewModel: ImageInfoViewModel,
    onBack: () -> Unit,
    onOpenUrl: (String) -> Unit
) {
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
                    title = Strings.IMAGE_INFO,
                    hasBack = true,
                    onBackClick = onBack,
                    includeStatusBarsPadding = true
                )
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            ImageInfoContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                viewModel = viewModel,
                onOpenUrl = onOpenUrl
            )
        }
    }
}
