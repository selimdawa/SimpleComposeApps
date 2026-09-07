package com.flatcode.simplecomposeapps.web

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.net.toUri
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flatcode.simplecomposeapps.utils.DATA
import com.flatcode.simplecomposeapps.web.ui.WebAboutDialog
import com.flatcode.simplecomposeapps.web.ui.WebAppScreen
import com.flatcode.simplecomposeapps.web.ui.WebSupportDialog
import com.flatcode.simplecomposeapps.web.ui.WebViewScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebAppActivity : ComponentActivity() {

    private val viewModel: WebAppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            WebNavHost(
                viewModel = viewModel,
                onShareApp = { shareApp() },
                onRateApp = { rateApp() },
                onEmail = { sendEmail() },
                onPhone = { callPhone() })
        }
    }

    private fun shareApp() {
        val share = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(
                Intent.EXTRA_TEXT,
                "Share App with\nhttps://play.google.com/store/apps/details?id=$packageName"
            )
        }
        startActivity(Intent.createChooser(share, "Share link!"))
    }

    private fun rateApp() {
        val uri = "market://details?id=$packageName".toUri()
        val intent = Intent(Intent.ACTION_VIEW, uri)
        try {
            startActivity(intent)
        } catch (_: Exception) {
            startActivity(Intent(Intent.ACTION_VIEW, "http://google.com".toUri()))
        }
    }

    private fun sendEmail() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = "mailto:${DATA.myEmail}".toUri()
        }
        startActivity(intent)
    }

    private fun callPhone() {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = "tel:${DATA.myMobileNumber}".toUri()
        }
        startActivity(intent)
    }
}

@Composable
fun WebNavHost(
    viewModel: WebAppViewModel,
    onShareApp: () -> Unit,
    onRateApp: () -> Unit,
    onEmail: () -> Unit,
    onPhone: () -> Unit
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "main",
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }) {
        composable("main") {
            val uiState by viewModel.uiState.collectAsState()
            WebAppScreen(
                onWebSite = { navController.navigate("webView/${DATA.WEBSITE}") },
                onInstagram = { navController.navigate("webView/${DATA.INSTAGRAM}") },
                onTwitter = { navController.navigate("webView/${DATA.TWITTER}") },
                onFacebook = { navController.navigate("webView/${DATA.FACEBOOK}") },
                onAboutUs = { viewModel.showAboutDialog(true) },
                onSupport = { viewModel.showSupportDialog(true) },
                onShareApp = onShareApp,
                onRateApp = onRateApp
            )

            if (uiState.showAboutDialog) {
                WebAboutDialog(onDismiss = { viewModel.showAboutDialog(false) })
            }

            if (uiState.showSupportDialog) {
                WebSupportDialog(
                    onDismiss = { viewModel.showSupportDialog(false) },
                    onEmail = onEmail,
                    onPhone = onPhone
                )
            }
        }
        composable("webView/{name}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val url = when (name) {
                DATA.WEBSITE -> DATA.mySite
                DATA.INSTAGRAM -> DATA.myInstagram
                DATA.FACEBOOK -> DATA.myFacebook
                DATA.TWITTER -> DATA.myTwitter
                else -> DATA.mySite
            }
            WebViewScreen(url = url)
        }
    }
}