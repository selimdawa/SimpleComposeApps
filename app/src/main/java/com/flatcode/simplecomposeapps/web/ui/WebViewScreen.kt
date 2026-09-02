package com.flatcode.simplecomposeapps.web.ui

import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebViewScreen(
    url: String
) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.loadsImagesAutomatically = true
                webViewClient = WebViewClient()
                scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
                loadUrl(url)
            }
        }, modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    )
}