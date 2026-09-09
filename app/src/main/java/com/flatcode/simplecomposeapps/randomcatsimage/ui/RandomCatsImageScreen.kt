package com.flatcode.simplecomposeapps.randomcatsimage.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flatcode.simplecomposeapps.randomcatsimage.RandomCatsImageViewModel
import com.flatcode.simplecomposeapps.ui.ToolbarContent
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ERROR
import com.flatcode.simplecomposeapps.ui.theme.MC_TRACK
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.DATA

@Composable
fun RandomCatsImageScreen(
    viewModel: RandomCatsImageViewModel, onDownload: (String) -> Unit
) {
    val isLoading by viewModel.isLoading.observeAsState(false)
    val imageUrl by viewModel.imageUrl.observeAsState("")

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

            if (isLoading) {
                CircularProgressIndicator(color = MC_TRACK)
            } else if (imageUrl.isEmpty()) {
                Text(
                    text = Strings.NONE_DISPLAY,
                    color = COLOR_ERROR,
                    fontSize = 32.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 20.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}