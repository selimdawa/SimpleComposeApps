package com.flatcode.simplecomposeapps.meals.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import com.flatcode.simplecomposeapps.meals.ui.CategoriesMealsScreen
import com.flatcode.simplecomposeapps.meals.ui.FavoritesMealsScreen
import com.flatcode.simplecomposeapps.meals.ui.HomeMealsScreen
import com.flatcode.simplecomposeapps.ui.ToolbarContent
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ERROR
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.Gray
import com.flatcode.simplecomposeapps.ui.theme.MC_TRACK
import com.flatcode.simplecomposeapps.utils.DATA
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MealsActivity : ComponentActivity() {

    @Serializable
    object Home

    @Serializable
    object Favorites

    @Serializable
    object Categories

    override fun onCreate(savedInstanceState: Bundle?) {
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
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

@Composable
fun MealsBottomNavigation(navController: NavHostController) {
    NavigationBar(
        containerColor = COLOR_ON_BACKGROUND, contentColor = COLOR_ERROR
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        DATA.MEALS_NAV.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        item.icon, contentDescription = item.label, modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text(item.label) },
                selected = currentDestination?.hierarchy?.any { it.route == item.route::class.qualifiedName } == true,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(DATA.MEALS_NAV[0].route) { saveState = true }
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
    navController: NavHostController, modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = DATA.MEALS_NAV[0].route,
        modifier = modifier
    ) {
        composable<MealsActivity.Home> {
            HomeMealsScreen(onMealClick = { id, name, thumb ->
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
        composable<MealsActivity.Favorites> {
            FavoritesMealsScreen(
                onMealClick = { id, name, thumb ->
                    val intent = Intent(context, MealDetailsActivity::class.java).apply {
                        putExtra(MealDetailsActivity.MEAL_ID, id)
                        putExtra(MealDetailsActivity.MEAL_NAME, name)
                        putExtra(MealDetailsActivity.MEAL_THUMB, thumb)
                    }
                    context.startActivity(intent)
                })
        }
        composable<MealsActivity.Categories> {
            CategoriesMealsScreen(
                onCategoryClick = { categoryName ->
                    val intent = Intent(context, CategoryMealsActivity::class.java).apply {
                        putExtra(CategoryMealsActivity.CATEGORY_NAME, categoryName)
                    }
                    context.startActivity(intent)
                })
        }
    }
}
