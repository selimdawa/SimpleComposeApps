package com.flatcode.simplecomposeapps.meals

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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.flatcode.simplecomposeapps.meals.ui.CategoriesMealsScreen
import com.flatcode.simplecomposeapps.meals.ui.CategoryMealsScreen
import com.flatcode.simplecomposeapps.meals.ui.FavoritesMealsScreen
import com.flatcode.simplecomposeapps.meals.ui.HomeMealsScreen
import com.flatcode.simplecomposeapps.meals.ui.MealDetailScreen
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.SimpleComposeAppsTheme
import com.flatcode.simplecomposeapps.utils.DATA
import dagger.hilt.android.AndroidEntryPoint
import io.selimdawa.multicolors.MultiColorManager

@AndroidEntryPoint
class MealsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        MultiColorManager.applyTheme(this)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            SimpleComposeAppsTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        MealsBottomNavigation(navController = navController)
                    }
                ) { paddingValues ->
                    MealsNavHost(
                        navController = navController,
                        modifier = Modifier.padding(paddingValues),
                        onBack = { finish() }
                    )
                }
            }
        }
    }
}

@Composable
fun MealsBottomNavigation(navController: NavHostController) {
    val items = listOf(
        Triple("home", "Home", AppIcons.Home),
        Triple("favorites", "Favorites", AppIcons.Favorite),
        Triple("categories", "Categories", AppIcons.Category)
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
                        popUpTo("home") { saveState = true }
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
fun MealsNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") {
            HomeMealsScreen(
                onBack = onBack,
                onMealClick = { id, name, thumb ->
                    navController.navigate("detail/$id/$name/${java.net.URLEncoder.encode(thumb, "UTF-8")}")
                },
                onCategoryClick = { categoryName ->
                    navController.navigate("category/$categoryName")
                }
            )
        }
        composable("favorites") {
            FavoritesMealsScreen(
                onBack = { navController.navigate("home") },
                onMealClick = { id, name, thumb ->
                    navController.navigate("detail/$id/$name/${java.net.URLEncoder.encode(thumb, "UTF-8")}")
                }
            )
        }
        composable("categories") {
            CategoriesMealsScreen(
                onBack = { navController.navigate("home") },
                onCategoryClick = { categoryName ->
                    navController.navigate("category/$categoryName")
                }
            )
        }
        composable(
            route = "detail/{id}/{name}/{thumb}",
            arguments = listOf(
                navArgument("id") { type = NavType.StringType },
                navArgument("name") { type = NavType.StringType },
                navArgument("thumb") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val thumb = backStackEntry.arguments?.getString("thumb") ?: ""
            MealDetailScreen(
                id = id,
                name = name,
                thumb = java.net.URLDecoder.decode(thumb, "UTF-8"),
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = "category/{categoryName}",
            arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
            CategoryMealsScreen(
                categoryName = categoryName,
                onBack = { navController.popBackStack() },
                onMealClick = { id, name, thumb ->
                    navController.navigate("detail/$id/$name/${java.net.URLEncoder.encode(thumb, "UTF-8")}")
                }
            )
        }
    }
}