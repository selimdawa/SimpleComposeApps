package com.flatcode.simplecomposeapps.todoNote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.navigation.NavDestination.Companion.hasRoute
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
import com.flatcode.simplecomposeapps.utils.DATA.COLOR_ERROR
import com.flatcode.simplecomposeapps.utils.DATA.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.Gray
import com.flatcode.simplecomposeapps.utils.DATA.MC_TRACK
import com.flatcode.simplecomposeapps.utils.DATA
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class TodoNoteActivity : ComponentActivity() {

    @Serializable
    object Tasks

    @Serializable
    object Notes

    @Serializable
    object AddEditTask

    @Serializable
    object AddEditNote

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = {
                    TodoBottomNavigation(navController = navController)
                },
                contentWindowInsets = WindowInsets(0, 0, 0, 0),
                containerColor = COLOR_ON_BACKGROUND
            ) { paddingValues ->
                TodoNavHost(
                    navController = navController,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    onBack = { finish() })
            }
        }
    }
}

@Composable
fun TodoBottomNavigation(navController: NavHostController) {
    NavigationBar(
        containerColor = COLOR_ON_BACKGROUND, contentColor = COLOR_ERROR
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        DATA.TODO_NAV.forEach { item ->
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
                        popUpTo(DATA.TODO_NAV[0].route) { saveState = true }
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
fun TodoNavHost(
    navController: NavHostController, onBack: () -> Unit, modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DATA.TODO_NAV[0].route,
        modifier = modifier,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }) {
        composable<TodoNoteActivity.Tasks> {
            TasksScreen(
                navController = navController,
                onBack = onBack,
                onAddTask = {
                    navController.currentBackStackEntry?.savedStateHandle?.set("task", null)
                    navController.navigate(TodoNoteActivity.AddEditTask)
                },
                onEditTask = { task ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("task", task)
                    navController.navigate(TodoNoteActivity.AddEditTask)
                })
        }
        composable<TodoNoteActivity.Notes> {
            NotesScreen(
                navController = navController,
                onBack = onBack,
                onAddNote = {
                    navController.currentBackStackEntry?.savedStateHandle?.set("note", null)
                    navController.navigate(TodoNoteActivity.AddEditNote)
                },
                onEditNote = { note ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("note", note)
                    navController.navigate(TodoNoteActivity.AddEditNote)
                })
        }
        composable<TodoNoteActivity.AddEditTask> {
            AddEditTaskScreen(
                onBack = { result: Int? ->
                    if (result != null) {
                        navController.previousBackStackEntry?.savedStateHandle?.set(
                            "add_edit_result", result
                        )
                    }
                    navController.popBackStack()
                })
        }
        composable<TodoNoteActivity.AddEditNote> {
            AddEditNoteScreen(
                onBack = { result: Int? ->
                    if (result != null) {
                        navController.previousBackStackEntry?.savedStateHandle?.set(
                            "add_edit_result", result
                        )
                    }
                    navController.popBackStack()
                })
        }
    }
}
