package com.flatcode.simplecomposeapps.news2

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.flatcode.simplecomposeapps.news2.ui.EverythingScreen
import com.flatcode.simplecomposeapps.news2.ui.TopArticlesScreen
import com.flatcode.simplecomposeapps.ui.AppIcons
import dagger.hilt.android.AndroidEntryPoint
import io.selimdawa.multicolors.MultiColorManager

@AndroidEntryPoint
class News2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        MultiColorManager.applyTheme(this)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = {
                    NewsBottomNavigation(navController = navController)
                }
            ) { paddingValues ->
                NewsNavHost(
                    navController = navController,
                    modifier = Modifier.padding(paddingValues),
                    onBack = { finish() }
                )
            }
        }
    }
}

@Composable
fun NewsBottomNavigation(navController: NavHostController) {
    val items = listOf(
        Triple("everything", "Everything", AppIcons.MultiDelete),
        Triple("topArticles", "Top Articles", AppIcons.News)
    )

    NavigationBar(
        containerColor = Color(0xFF212121),
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { (route, label, icon) ->
            NavigationBarItem(
                icon = { Icon(icon, contentDescription = label, modifier = Modifier.size(24.dp)) },
                label = { Text(label) },
                selected = currentDestination?.hierarchy?.any { it.route == route } == true,
                onClick = {
                    navController.navigate(route) {
                        popUpTo("everything") { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF339999),
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = Color(0xFF339999),
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Composable
fun NewsNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = "everything",
        modifier = modifier
    ) {
        composable("everything") {
            EverythingScreen(onBack = onBack)
        }
        composable("topArticles") {
            TopArticlesScreen(onBack = onBack)
        }
    }
}