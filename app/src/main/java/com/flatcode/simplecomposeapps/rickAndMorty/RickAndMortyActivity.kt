package com.flatcode.simplecomposeapps.rickAndMorty

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
import com.flatcode.simplecomposeapps.rickAndMorty.ui.RickCharactersScreen
import com.flatcode.simplecomposeapps.rickAndMorty.ui.RickEpisodesScreen
import com.flatcode.simplecomposeapps.rickAndMorty.ui.RickLocationsScreen
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.ToolbarContent
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ERROR
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.Gray
import com.flatcode.simplecomposeapps.ui.theme.MC_TRACK
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.DATA
import dagger.hilt.android.AndroidEntryPoint
import io.selimdawa.multicolors.MultiColorManager

@AndroidEntryPoint
class RickAndMortyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        MultiColorManager.applyTheme(this)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            Scaffold(
                topBar = {
                ToolbarContent(
                    title = DATA.RICK_AND_MORTY, hasBack = false
                )
            }, bottomBar = {
                RickBottomNavigation(navController = navController)
            }, containerColor = COLOR_ON_BACKGROUND
            ) { paddingValues ->
                RickNavHost(
                    navController = navController,
                    modifier = Modifier.padding(paddingValues),
                    onBack = { finish() })
            }
        }
    }
}

@Composable
fun RickBottomNavigation(navController: NavHostController) {
    val items = listOf(
        Triple(Strings.CHARACTER, Strings.CHARACTER, AppIcons.RickAndMorty),
        Triple(Strings.LOCATION, Strings.LOCATION, AppIcons.Location),
        Triple(Strings.EPISODE, Strings.EPISODE, AppIcons.EventNote)
    )

    NavigationBar(
        containerColor = COLOR_ON_BACKGROUND, contentColor = COLOR_ERROR
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { (route, label, icon) ->
            NavigationBarItem(
                icon = {
                Icon(
                    icon, contentDescription = label, modifier = Modifier.size(24.dp)
                )
            },
                label = { Text(label) },
                selected = currentDestination?.hierarchy?.any { it.route == route } == true,
                onClick = {
                    navController.navigate(route) {
                        popUpTo(Strings.CHARACTER) { saveState = true }
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
fun RickNavHost(
    navController: NavHostController, modifier: Modifier = Modifier, onBack: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Strings.CHARACTER,
        modifier = modifier) {
        composable(Strings.CHARACTER) {
            RickCharactersScreen(onBack = onBack)
        }
        composable(Strings.LOCATION) {
            RickLocationsScreen(onBack = { navController.navigate(Strings.CHARACTER) })
        }
        composable(Strings.EPISODE) {
            RickEpisodesScreen(onBack = { navController.navigate(Strings.CHARACTER) })
        }
    }
}