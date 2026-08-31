package com.flatcode.simplecomposeapps.blogger.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.flatcode.simplecomposeapps.blogger.ui.BloggerScreen
import com.flatcode.simplecomposeapps.blogger.viewmodel.BloggerViewModel
import com.flatcode.simplecomposeapps.ui.theme.SimpleComposeAppsTheme
import com.flatcode.simplecomposeapps.utils.openActivity

class BloggerAppActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[BloggerViewModel::class.java]

        setContent {
            SimpleComposeAppsTheme {
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
}