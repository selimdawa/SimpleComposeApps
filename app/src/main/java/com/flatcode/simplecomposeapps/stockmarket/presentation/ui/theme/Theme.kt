package com.flatcode.simplecomposeapps.stockmarket.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color.Cyan,
    background = DarkBlue,
    onPrimary = Color.DarkGray,
    onBackground = TextWhite,
    surface = DarkBlue,
    onSurface = TextWhite
)

private val LightColorScheme = lightColorScheme(
    primary = Color.Cyan,
    background = Color.White,
    onPrimary = Color.DarkGray,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black
)

@Composable
fun StockMarketAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}