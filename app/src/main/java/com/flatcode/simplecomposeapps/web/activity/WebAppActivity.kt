package com.flatcode.simplecomposeapps.web.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.flatcode.simplecomposeapps.ui.ToolbarContent
import com.flatcode.simplecomposeapps.ui.theme.AppIcons
import com.flatcode.simplecomposeapps.utils.DATA.COLOR_ERROR
import com.flatcode.simplecomposeapps.utils.DATA.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.Gray
import com.flatcode.simplecomposeapps.utils.DATA.MC_TRACK
import com.flatcode.simplecomposeapps.utils.DATA
import com.flatcode.simplecomposeapps.web.viewmodel.WebAppViewModel
import com.flatcode.simplecomposeapps.web.ui.WebBookmarksScreen
import com.flatcode.simplecomposeapps.web.ui.WebHistoryScreen
import com.flatcode.simplecomposeapps.web.ui.WebMainScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class WebAppActivity : ComponentActivity() {

    @Serializable
    object Home

    @Serializable
    object History

    @Serializable
    object Bookmarks

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val viewModel: WebAppViewModel = hiltViewModel()

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            Scaffold(
                topBar = {
                    ToolbarContent(
                        title = DATA.WEB,
                        hasBack = false,
                        actions = {
                            if (currentDestination?.hierarchy?.any {
                                    it.hasRoute(History::class) || it.hasRoute(Bookmarks::class)
                                } == true) {
                                CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides 0.dp) {
                                    Box(
                                        modifier = Modifier
                                            .size(30.dp)
                                            .clickable(
                                                interactionSource = remember { MutableInteractionSource() },
                                                indication = null,
                                                onClick = {
                                                    if (currentDestination.hierarchy.any { it.hasRoute(History::class) }) {
                                                        viewModel.clearHistory()
                                                    } else {
                                                        viewModel.clearBookmarks()
                                                    }
                                                }
                                            )
                                    ) {
                                        Icon(
                                            imageVector = AppIcons.Delete,
                                            contentDescription = "Clear",
                                            tint = Color.White,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                }
                            }
                        }
                    )
                }, bottomBar = {
                    WebBottomNavigation(navController = navController)
                }, containerColor = COLOR_ON_BACKGROUND
            ) { paddingValues ->
                WebNavHost(
                    navController = navController,
                    modifier = Modifier.padding(paddingValues),
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun WebBottomNavigation(navController: NavHostController) {
    NavigationBar(
        containerColor = COLOR_ON_BACKGROUND, contentColor = COLOR_ERROR
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        DATA.WEB_NAV.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        item.icon, contentDescription = item.label, modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text(item.label) },
                selected = currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(DATA.WEB_NAV[0].route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MC_TRACK,
                    unselectedIconColor = Gray,
                    selectedTextColor = MC_TRACK,
                    unselectedTextColor = Gray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Composable
fun WebNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: WebAppViewModel
) {
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = DATA.WEB_NAV[0].route,
        modifier = modifier,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }) {
        composable<WebAppActivity.Home> { WebMainScreen(viewModel = viewModel) }
        composable<WebAppActivity.History> {
            WebHistoryScreen(
                viewModel = viewModel, onNavigateToUrl = { url ->
                    val intent = Intent(context, WebViewActivity::class.java).apply {
                        putExtra("url", url)
                    }
                    context.startActivity(intent)
                })
        }
        composable<WebAppActivity.Bookmarks> {
            WebBookmarksScreen(
                viewModel = viewModel, onNavigateToUrl = { url ->
                    val intent = Intent(context, WebViewActivity::class.java).apply {
                        putExtra("url", url)
                    }
                    context.startActivity(intent)
                })
        }
    }
}
