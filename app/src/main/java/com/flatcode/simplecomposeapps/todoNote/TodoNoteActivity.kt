package com.flatcode.simplecomposeapps.todoNote

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
import com.flatcode.simplecomposeapps.todoNote.ui.AddEditNoteScreen
import com.flatcode.simplecomposeapps.todoNote.ui.AddEditTaskScreen
import com.flatcode.simplecomposeapps.todoNote.ui.NotesScreen
import com.flatcode.simplecomposeapps.todoNote.ui.TasksScreen
import com.flatcode.simplecomposeapps.ui.AppIcons
import dagger.hilt.android.AndroidEntryPoint
import io.selimdawa.multicolors.MultiColorManager

@AndroidEntryPoint
class TodoNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        MultiColorManager.applyTheme(this)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = {
                    TodoBottomNavigation(navController = navController)
                }
            ) { paddingValues ->
                TodoNavHost(
                    navController = navController,
                    modifier = Modifier.padding(paddingValues),
                    onBack = { finish() }
                )
            }
        }
    }
}

@Composable
fun TodoBottomNavigation(navController: NavHostController) {
    val items = listOf(
        Triple("tasks", "Tasks", AppIcons.TodoCheck),
        Triple("notes", "Notes", AppIcons.TodoNote)
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
                        popUpTo("tasks") { saveState = true }
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
fun TodoNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = "tasks",
        modifier = modifier
    ) {
        composable("tasks") {
            TasksScreen(
                onBack = onBack,
                onAddTask = { navController.navigate("addEditTask") },
                onEditTask = { task -> 
                    // Need to handle task serialization
                }
            )
        }
        composable("notes") {
            NotesScreen(
                onBack = onBack,
                onAddNote = { navController.navigate("addEditNote") },
                onEditNote = { note ->
                    // Need to handle note serialization
                }
            )
        }
        composable("addEditTask") {
            AddEditTaskScreen(onBack = { navController.popBackStack() })
        }
        composable("addEditNote") {
            AddEditNoteScreen(onBack = { navController.popBackStack() })
        }
    }
}