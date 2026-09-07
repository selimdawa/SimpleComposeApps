package com.flatcode.simplecomposeapps.blogger.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.flatcode.simplecomposeapps.blogger.ui.BloggerPagesScreen
import com.flatcode.simplecomposeapps.blogger.viewmodel.BloggerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PagesActivity : ComponentActivity() {

    private val viewModel: BloggerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            BloggerPagesScreen(
                viewModel = viewModel,
                onBack = { onBackPressedDispatcher.onBackPressed() },
                onPageClick = { pageId ->
                    val intent = Intent(this, PageDetailsActivity::class.java)
                    intent.putExtra("pageId", pageId)
                    startActivity(intent)
                })
        }
    }
}