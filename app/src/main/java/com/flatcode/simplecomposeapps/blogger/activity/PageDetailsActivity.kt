package com.flatcode.simplecomposeapps.blogger.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.flatcode.simplecomposeapps.blogger.ui.BloggerDetailsScreen
import com.flatcode.simplecomposeapps.blogger.viewmodel.BloggerViewModel
import com.flatcode.simplecomposeapps.ui.theme.SimpleComposeAppsTheme
import com.flatcode.simplecomposeapps.ui.theme.SimpleComposeAppsTheme

class PageDetailsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[BloggerViewModel::class.java]
        val pageId = intent.getStringExtra("pageId") ?: ""

        setContent {
            SimpleComposeAppsTheme {
                BloggerDetailsScreen(
                    viewModel = viewModel,
                    id = pageId,
                    isPage = true,
                    onBack = { onBackPressedDispatcher.onBackPressed() })
            }
        }
    }
}