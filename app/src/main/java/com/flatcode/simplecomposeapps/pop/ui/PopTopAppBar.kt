package com.flatcode.simplecomposeapps.pop.ui

import androidx.compose.runtime.Composable
import com.flatcode.simplecomposeapps.ui.ToolbarContent

@Composable
fun PopTopAppBar(
    title: String,
    onBack: () -> Unit
) {
    ToolbarContent(
        title = title,
        hasBack = true,
        onBackClick = onBack,
    )
}