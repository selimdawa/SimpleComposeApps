package com.flatcode.simplecomposeapps.meals.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import android.content.Intent
import com.flatcode.simplecomposeapps.meals.ui.CategoriesMealsScreen
import com.flatcode.simplecomposeapps.meals.ui.FavoritesMealsScreen
import com.flatcode.simplecomposeapps.meals.ui.HomeMealsScreen
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.ToolbarContent
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ERROR
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.Gray
import com.flatcode.simplecomposeapps.ui.theme.MC_TRACK
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
            val navController = rememberNavController()
            Scaffold(containerColor = COLOR_ON_BACKGROUND, topBar = {
                ToolbarContent(
                    title = DATA.MEALS, hasBack = false
                )
            }, bottomBar = {
                MealsBottomNavigation(navController = navController)
            }) { paddingValues ->
                MealsNavHost(
                    navController = navController,
                    modifier = Modifier.padding(paddingValues),
                    onBack = { finish() })
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
                        popUpTo("home") { saveState = true }
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
fun MealsNavHost(
    navController: NavHostController, modifier: Modifier = Modifier, onBack: () -> Unit
) {
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }) {
        composable("home") {
            HomeMealsScreen(onBack = onBack, onMealClick = { id, name, thumb ->
                val intent = Intent(context, MealDetailsActivity::class.java).apply {
                    putExtra(MealDetailsActivity.MEAL_ID, id)
                    putExtra(MealDetailsActivity.MEAL_NAME, name)
                    putExtra(MealDetailsActivity.MEAL_THUMB, thumb)
                }
                context.startActivity(intent)
            }, onCategoryClick = { categoryName ->
                val intent = Intent(context, CategoryMealsActivity::class.java).apply {
                    putExtra(CategoryMealsActivity.CATEGORY_NAME, categoryName)
                }
                context.startActivity(intent)
            })
        }
        composable("favorites") {
            FavoritesMealsScreen(
                onBack = { navController.navigate("home") },
                onMealClick = { id, name, thumb ->
                    val intent = Intent(context, MealDetailsActivity::class.java).apply {
                        putExtra(MealDetailsActivity.MEAL_ID, id)
                        putExtra(MealDetailsActivity.MEAL_NAME, name)
                        putExtra(MealDetailsActivity.MEAL_THUMB, thumb)
                    }
                    context.startActivity(intent)
                })
        }
        composable("categories") {
            CategoriesMealsScreen(
                onBack = { navController.navigate("home") },
                onCategoryClick = { categoryName ->
                    val intent = Intent(context, CategoryMealsActivity::class.java).apply {
                        putExtra(CategoryMealsActivity.CATEGORY_NAME, categoryName)
                    }
                    context.startActivity(intent)
                })
        }
    }
}