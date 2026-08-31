package com.flatcode.simplecomposeapps.newsapp.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.os.BundleCompat
import com.flatcode.simplecomposeapps.newsapp.model.NewsHeadlines
import com.flatcode.simplecomposeapps.newsapp.ui.NewsAppDetailsScreen
import com.flatcode.simplecomposeapps.ui.theme.SimpleComposeAppsTheme
import com.flatcode.simplecomposeapps.utils.DATA

class NewsAppDetailsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val headlines = intent.extras?.let { bundle ->
            BundleCompat.getSerializable(bundle, DATA.DATA, NewsHeadlines::class.java)
        }

        if (headlines == null) {
            finish()
            return
        }

        setContent {
            SimpleComposeAppsTheme {
                NewsAppDetailsScreen(
                    headline = headlines,
                    onBack = { finish() }
                )
            }
        }
    }
}
