package com.flatcode.simplecomposeapps.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.flatcode.simplecomposeapps.main.ui.MainAboutDialog
import com.flatcode.simplecomposeapps.main.ui.MainScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private val mainInfoViewModel: MainInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            var showAboutDialog by remember { mutableStateOf(false) }

            MainScreen(
                viewModel = mainViewModel, onInfoClick = {
                    mainInfoViewModel.getInfoItems()
                    showAboutDialog = true
                })

            if (showAboutDialog) {
                MainAboutDialog(
                    infoViewModel = mainInfoViewModel, onDismiss = { showAboutDialog = false })
            }
        }
    }
}