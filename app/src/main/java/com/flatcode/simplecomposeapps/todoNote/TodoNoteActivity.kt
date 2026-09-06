package com.flatcode.simplecomposeapps.todoNote

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ERROR
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.Gray
import com.flatcode.simplecomposeapps.ui.theme.MC_TRACK
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.DATA
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
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
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
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = DATA.TODO_NAV[0].route,
        modifier = modifier
    ) {
        DATA.TODO_NAV.forEach { item ->
            composable(item.route) {
                when (item.route) {
                    Strings.TASKS -> TasksScreen(
                        navController = navController,
                        onBack = onBack,
                        onAddTask = {
                            navController.currentBackStackEntry?.savedStateHandle?.set("task", null)
                            navController.navigate(Strings.ADD_EDIT_TASK)
                        },
                        onEditTask = { task ->
                            navController.currentBackStackEntry?.savedStateHandle?.set("task", task)
                            navController.navigate(Strings.ADD_EDIT_TASK)
                        })

                    Strings.NOTES -> NotesScreen(
                        navController = navController,
                        onBack = onBack,
                        onAddNote = {
                            navController.currentBackStackEntry?.savedStateHandle?.set("note", null)
                            navController.navigate(Strings.ADD_EDIT_NOTE)
                        },
                        onEditNote = { note ->
                            navController.currentBackStackEntry?.savedStateHandle?.set("note", note)
                            navController.navigate(Strings.ADD_EDIT_NOTE)
                        })
                }
            }
        }
        composable(Strings.ADD_EDIT_TASK) {
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
        composable(Strings.ADD_EDIT_NOTE) {
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