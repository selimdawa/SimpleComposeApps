package com.flatcode.simplecomposeapps.blogger.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.flatcode.simplecomposeapps.blogger.ui.BloggerDetailsScreen
import com.flatcode.simplecomposeapps.blogger.viewmodel.BloggerViewModel

class PostDetailsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[BloggerViewModel::class.java]
        val postId = intent.getStringExtra("postId") ?: ""

        setContent {
            BloggerDetailsScreen(
                viewModel = viewModel,
                id = postId,
                isPage = false,
                onBack = { onBackPressedDispatcher.onBackPressed() })
        }
    }
}