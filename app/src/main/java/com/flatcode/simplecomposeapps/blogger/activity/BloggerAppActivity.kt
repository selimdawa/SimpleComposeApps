package com.flatcode.simplecomposeapps.blogger.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.flatcode.simplecomposeapps.blogger.ui.BloggerScreen
import com.flatcode.simplecomposeapps.blogger.viewmodel.BloggerViewModel
import com.flatcode.simplecomposeapps.utils.openActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BloggerAppActivity : ComponentActivity() {

    private val viewModel: BloggerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            BloggerScreen(
                viewModel = viewModel,
                onBack = { onBackPressedDispatcher.onBackPressed() },
                onPagesClick = { openActivity(PagesActivity::class.java) },
                onPostClick = { postId ->
                    val intent = Intent(this, PostDetailsActivity::class.java)
                    intent.putExtra("postId", postId)
                    startActivity(intent)
                })
        }
    }
}