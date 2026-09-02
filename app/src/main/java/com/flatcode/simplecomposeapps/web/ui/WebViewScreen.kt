package com.flatcode.simplecomposeapps.web.ui

import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebViewScreen(
    url: String,
    title: String,
    onBack: () -> Unit
) {
    val mcTrack = AppTheme.colors.track

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = title, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = AppIcons.Back, contentDescription = null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = mcTrack)
            )
        }
    ) { paddingValues ->
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    settings.loadsImagesAutomatically = true
                    webViewClient = WebViewClient()
                    scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
                    loadUrl(url)
                }
            },
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        )
    }
}
