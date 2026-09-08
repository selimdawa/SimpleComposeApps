package com.flatcode.simplecomposeapps.web.ui

import android.content.Intent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.flatcode.simplecomposeapps.ui.ToolbarContent
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.DATA
import com.flatcode.simplecomposeapps.web.WebAppUiState
import com.flatcode.simplecomposeapps.web.WebAppViewModel
import com.flatcode.simplecomposeapps.web.WebBottomNavigation

@Composable
fun WebMainScreen(
    viewModel: WebAppViewModel
) {
    val context = LocalContext.current
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val uiState by viewModel.uiState.collectAsState()

    val onShareApp = {
        val share = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(
                Intent.EXTRA_TEXT,
                "Share App with\nhttps://play.google.com/store/apps/details?id=${context.packageName}"
            )
        }
        context.startActivity(Intent.createChooser(share, "Share link!"))
    }

    val onRateApp = {
        val uri = "market://details?id=${context.packageName}".toUri()
        val intent = Intent(Intent.ACTION_VIEW, uri)
        try {
            context.startActivity(intent)
        } catch (_: Exception) {
            context.startActivity(Intent(Intent.ACTION_VIEW, "http://google.com".toUri()))
        }
    }

    val onEmail = {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = "mailto:${DATA.myEmail}".toUri()
        }
        context.startActivity(intent)
    }

    val onPhone = {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = "tel:${DATA.myMobileNumber}".toUri()
        }
        context.startActivity(intent)
    }

    val showNav = DATA.WEB_NAV.any { it.route == currentDestination?.route }

    Scaffold(containerColor = COLOR_ON_BACKGROUND, topBar = {
        ToolbarContent(
            title = DATA.WEB, hasBack = false, includeStatusBarsPadding = true
        )
    }, bottomBar = {
        if (showNav) {
            WebBottomNavigation(navController = navController)
        }
    }) { paddingValues ->
        WebNavHost(
            navController = navController,
            viewModel = viewModel,
            uiState = uiState,
            onShareApp = onShareApp,
            onRateApp = onRateApp,
            onEmail = onEmail,
            onPhone = onPhone,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun WebNavHost(
    navController: NavHostController,
    viewModel: WebAppViewModel,
    uiState: WebAppUiState,
    onShareApp: () -> Unit,
    onRateApp: () -> Unit,
    onEmail: () -> Unit,
    onPhone: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DATA.WEB_NAV[0].route,
        modifier = modifier.fillMaxSize(),
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }) {
        DATA.WEB_NAV.forEach { item ->
            composable(item.route) {
                when (item.route) {
                    Strings.HOME -> {
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

                    Strings.HISTORY -> {
                        WebHistoryScreen(
                            viewModel = viewModel, onNavigateToUrl = { url ->
                                navController.navigate("webView_direct?url=$url")
                            })
                    }

                    Strings.BOOKMARKS -> {
                        WebBookmarksScreen(
                            viewModel = viewModel, onNavigateToUrl = { url ->
                                navController.navigate("webView_direct?url=$url")
                            })
                    }
                }
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
            WebViewScreen(
                url = url, viewModel = viewModel
            )
        }
        composable("webView_direct?url={url}") { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url") ?: ""
            WebViewScreen(
                url = url, viewModel = viewModel
            )
        }
    }
}