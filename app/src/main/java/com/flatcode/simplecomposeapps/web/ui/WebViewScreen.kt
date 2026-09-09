package com.flatcode.simplecomposeapps.web.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ERROR
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.web.viewmodel.WebAppUiState
import com.flatcode.simplecomposeapps.web.viewmodel.WebAppViewModel

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(
    url: String,
    viewModel: WebAppViewModel
) {
    val uiState by viewModel.uiState.observeAsState(WebAppUiState())
    var webView by remember { mutableStateOf<WebView?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(COLOR_ON_BACKGROUND)
    ) {
        AndroidView(
            factory = { context ->
                    WebView(context).apply {
                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true
                        settings.loadsImagesAutomatically = true
                        webViewClient = object : WebViewClient() {
                            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                                super.onPageStarted(view, url, favicon)
                                viewModel.setLoading(true)
                            }

                            override fun onPageFinished(view: WebView?, url: String?) {
                                super.onPageFinished(view, url)
                                viewModel.setLoading(false)
                                val title = view?.title ?: ""
                                val currentUrl = url ?: ""
                                viewModel.updateCurrentPage(title, currentUrl)
                                viewModel.addHistory(title, currentUrl)
                            }
                        }
                        scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
                        loadUrl(url)
                        webView = this
                    }
                },
                modifier = Modifier.fillMaxSize()
            )

            if (uiState.isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter),
                    color = COLOR_ERROR,
                    trackColor = COLOR_ON_BACKGROUND
                )
            }
        }
}