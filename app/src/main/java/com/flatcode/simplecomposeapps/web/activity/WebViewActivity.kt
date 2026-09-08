package com.flatcode.simplecomposeapps.web.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ERROR
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.web.viewmodel.WebAppViewModel
import com.flatcode.simplecomposeapps.web.ui.WebViewScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val url = intent.getStringExtra("url") ?: ""

        setContent {
            val viewModel: WebAppViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            
            // Check if current page is bookmarked
            val isBookmarked = uiState.bookmarks.any { it.url == uiState.currentUrl }

            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { 
                            viewModel.toggleBookmark(uiState.currentTitle, uiState.currentUrl)
                        },
                        containerColor = COLOR_ERROR,
                        contentColor = COLOR_ON_BACKGROUND
                    ) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Toggle Bookmark"
                        )
                    }
                }
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(COLOR_ON_BACKGROUND)
                        .padding(paddingValues)
                ) {
                    WebViewScreen(url = url, viewModel = viewModel)
                }
            }
        }
    }
}
