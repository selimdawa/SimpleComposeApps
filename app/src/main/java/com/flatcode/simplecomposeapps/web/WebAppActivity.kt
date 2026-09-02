package com.flatcode.simplecomposeapps.web

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.flatcode.simplecomposeapps.ui.theme.SimpleComposeAppsTheme
import com.flatcode.simplecomposeapps.utils.DATA
import com.flatcode.simplecomposeapps.web.ui.WebAboutDialog
import com.flatcode.simplecomposeapps.web.ui.WebAppScreen
import com.flatcode.simplecomposeapps.web.ui.WebSupportDialog
import com.flatcode.simplecomposeapps.web.viewmodel.WebAppViewModel

class WebAppActivity : ComponentActivity() {

    private val viewModel: WebAppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            SimpleComposeAppsTheme {
                val uiState by viewModel.uiState.collectAsState()

                WebAppScreen(
                    onWebSite = { openWebView(DATA.WEBSITE) },
                    onInstagram = { openWebView(DATA.INSTAGRAM) },
                    onTwitter = { openWebView(DATA.TWITTER) },
                    onFacebook = { openWebView(DATA.FACEBOOK) },
                    onAboutUs = { viewModel.showAboutDialog(true) },
                    onSupport = { viewModel.showSupportDialog(true) },
                    onShareApp = { shareApp() },
                    onRateApp = { rateApp() }
                )

                if (uiState.showAboutDialog) {
                    WebAboutDialog(onDismiss = { viewModel.showAboutDialog(false) })
                }

                if (uiState.showSupportDialog) {
                    WebSupportDialog(
                        onDismiss = { viewModel.showSupportDialog(false) },
                        onEmail = { sendEmail() },
                        onPhone = { callPhone() }
                    )
                }
            }
        }
    }

    private fun openWebView(name: String) {
        val intent = Intent(this, WebViewActivity::class.java).apply {
            putExtra(DATA.WEB_NAME, name)
        }
        startActivity(intent)
    }

    private fun shareApp() {
        val share = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "Share App with\nhttps://play.google.com/store/apps/details?id=$packageName")
        }
        startActivity(Intent.createChooser(share, "Share link!"))
    }

    private fun rateApp() {
        val uri = Uri.parse("market://details?id=$packageName")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        try {
            startActivity(intent)
        } catch (_: Exception) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://google.com")))
        }
    }

    private fun sendEmail() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:${DATA.myEmail}")
        }
        startActivity(intent)
    }

    private fun callPhone() {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:${DATA.myMobileNumber}")
        }
        startActivity(intent)
    }
}
