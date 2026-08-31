package com.flatcode.simplecomposeapps.randomimagegenerating.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.flatcode.simplecomposeapps.R
import com.flatcode.simplecomposeapps.randomimagegenerating.RandomImageGeneratingViewModel
import com.flatcode.simplecomposeapps.ui.CommonTopAppBar
import com.flatcode.simplecomposeapps.ui.theme.Strings

@Composable
fun RandomImageGeneratingScreen(
    viewModel: RandomImageGeneratingViewModel,
    onBack: () -> Unit,
    onNavigateToInfo: () -> Unit,
    onDownload: (String) -> Unit
) {
    val isLoading by viewModel.isLoading
    val imageUrl by viewModel.imageUrl

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CommonTopAppBar(
                title = Strings.RANDOM_IMAGE_GENERATING,
                onBack = onBack
            )
        },
        containerColor = MaterialTheme.colorScheme.background
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
