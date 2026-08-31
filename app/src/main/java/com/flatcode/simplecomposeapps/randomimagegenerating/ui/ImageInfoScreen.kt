package com.flatcode.simplecomposeapps.randomimagegenerating.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.flatcode.simplecomposeapps.R
import com.flatcode.simplecomposeapps.randomimagegenerating.ImageInfoViewModel
import com.flatcode.simplecomposeapps.ui.CommonTopAppBar
import com.flatcode.simplecomposeapps.ui.theme.Strings

@Composable
fun ImageInfoScreen(
    viewModel: ImageInfoViewModel,
    onBack: () -> Unit,
    onOpenUrl: (String) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CommonTopAppBar(
                title = Strings.IMAGE_INFO,
                onBack = onBack
            )
        },
        containerColor = MaterialTheme.colorScheme.background
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
