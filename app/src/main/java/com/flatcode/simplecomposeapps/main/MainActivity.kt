package com.flatcode.simplecomposeapps.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelProvider
import com.flatcode.simplecomposeapps.main.ui.MainAboutDialog
import com.flatcode.simplecomposeapps.main.ui.MainScreen

class MainActivity : ComponentActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainInfoViewModel: MainInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mainInfoViewModel = ViewModelProvider(this)[MainInfoViewModel::class.java]

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

        mainViewModel.getItems()
    }
}