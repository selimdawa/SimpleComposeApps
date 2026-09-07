package com.flatcode.simplecomposeapps.blogger.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.flatcode.simplecomposeapps.blogger.ui.BloggerDetailsScreen
import com.flatcode.simplecomposeapps.blogger.viewmodel.BloggerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PageDetailsActivity : ComponentActivity() {

    private val viewModel: BloggerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val pageId = intent.getStringExtra("pageId") ?: ""

        setContent {
            BloggerDetailsScreen(
                viewModel = viewModel,
                id = pageId,
                isPage = true,
                onBack = { onBackPressedDispatcher.onBackPressed() })
        }
    }
}