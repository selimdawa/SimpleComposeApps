package com.flatcode.simplecomposeapps.dictionary

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flatcode.simplecomposeapps.dictionary.ui.DefinitionScreen
import com.flatcode.simplecomposeapps.dictionary.ui.DictionaryScreen
import com.flatcode.simplecomposeapps.ui.theme.SimpleComposeAppsTheme
import dagger.hilt.android.AndroidEntryPoint
import io.selimdawa.multicolors.MultiColorManager

@AndroidEntryPoint
class DictionaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        MultiColorManager.applyTheme(this)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            SimpleComposeAppsTheme {
                DictionaryAppNavHost(onBack = { finish() })
            }
        }
    }
}

@Composable
fun DictionaryAppNavHost(onBack: () -> Unit) {
    val navController = rememberNavController()
    val viewModel: DictionaryViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = "search") {
        composable("search") {
            DictionaryScreen(
                viewModel = viewModel,
                onBack = onBack,
                onDefinitionFound = {
                    navController.navigate("definition")
                }
            )
        }
        composable("definition") {
            DefinitionScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}