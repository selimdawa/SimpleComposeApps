package com.flatcode.simplecomposeapps.web.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.flatcode.simplecomposeapps.ui.ToolbarContent
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ERROR
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.Gray
import com.flatcode.simplecomposeapps.ui.theme.MC_TRACK
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.DATA
import com.flatcode.simplecomposeapps.web.viewmodel.WebAppViewModel
import com.flatcode.simplecomposeapps.web.ui.WebBookmarksScreen
import com.flatcode.simplecomposeapps.web.ui.WebHistoryScreen
import com.flatcode.simplecomposeapps.web.ui.WebMainScreen
import dagger.hilt.android.AndroidEntryPoint
import io.selimdawa.multicolors.MultiColorManager

@AndroidEntryPoint
class WebAppActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        MultiColorManager.applyTheme(this)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            Scaffold(
                topBar = {
                ToolbarContent(
                    title = DATA.WEB, hasBack = false
                )
            }, bottomBar = {
                WebBottomNavigation(navController = navController)
            }, containerColor = COLOR_ON_BACKGROUND
            ) { paddingValues ->
                WebNavHost(
                    navController = navController, modifier = Modifier.padding(paddingValues)
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
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
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
    navController: NavHostController, modifier: Modifier = Modifier
) {
    val viewModel: WebAppViewModel = hiltViewModel()
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = DATA.WEB_NAV[0].route,
        modifier = modifier,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }) {
        DATA.WEB_NAV.forEach { item ->
            composable(item.route) {
                when (item.route) {
                    Strings.HOME -> WebMainScreen(viewModel = viewModel)
                    Strings.HISTORY -> WebHistoryScreen(
                        viewModel = viewModel, onNavigateToUrl = { url ->
                            val intent = Intent(context, WebViewActivity::class.java).apply {
                                putExtra("url", url)
                            }
                            context.startActivity(intent)
                        })

                    Strings.BOOKMARKS -> WebBookmarksScreen(
                        viewModel = viewModel, onNavigateToUrl = { url ->
                            val intent = Intent(context, WebViewActivity::class.java).apply {
                                putExtra("url", url)
                            }
                            context.startActivity(intent)
                        })
                }
            }
        }
    }
}