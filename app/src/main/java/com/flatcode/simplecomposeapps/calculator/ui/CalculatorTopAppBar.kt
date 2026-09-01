package com.flatcode.simplecomposeapps.calculator.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.utils.DATA

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorTopAppBar(
    onBack: () -> Unit,
    onClearHistory: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = DATA.CALCULATOR,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = AppIcons.Back,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(onClick = onClearHistory) {
                Icon(
                    imageVector = AppIcons.ClearAll,
                    contentDescription = "Clear History",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}