package com.flatcode.simplecomposeapps.ui.theme

import android.app.Activity
import android.util.TypedValue
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import io.selimdawa.multicolors.MultiColorManager

data class ExtraColors(
    val primaryGradient: Brush = Brush.linearGradient(listOf(Color.Gray, Color.Gray))
)

val LocalAppColors = staticCompositionLocalOf { ExtraColors() }

object AppTheme {
    val colors: ExtraColors
        @Composable @ReadOnlyComposable get() = LocalAppColors.current
}

@Composable
fun rememberAttributeColor(attrName: String, fallback: Color, themeId: String): Color {
    val context = LocalContext.current
    return remember(context, attrName, themeId) {
        val typedValue = TypedValue()
        var attributeResId = context.resources.getIdentifier(attrName, "attr", context.packageName)
        if (attributeResId == 0) {
            attributeResId =
                context.resources.getIdentifier(attrName, "attr", "io.selimdawa.multicolors")
        }

        if (attributeResId != 0 && context.theme.resolveAttribute(
                attributeResId, typedValue, true
            )
        ) {
            if (typedValue.type >= TypedValue.TYPE_FIRST_COLOR_INT && typedValue.type <= TypedValue.TYPE_LAST_COLOR_INT) {
                // It's a direct color value
                Color(typedValue.data)
            } else if (typedValue.resourceId != 0) {
                // It's a reference to a color resource
                Color(ContextCompat.getColor(context, typedValue.resourceId))
            } else {
                fallback
            }
        } else fallback
    }
}

@Composable
fun SimpleComposeAppsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit
) {
    // Observe theme changes from the MultiColors library
    val themeId by MultiColorManager.currentThemeId.collectAsState()

    // Fetch colors from the selected theme (e.g., G2 themes)
    val mcTrack = rememberAttributeColor("mc_track", McTrackColor, themeId)
    val mcTrack2 = rememberAttributeColor("mc_track_2", mcTrack, themeId)
    val mcBg = rememberAttributeColor("mc_bg", McBgColor, themeId)

    val colorPrimary = rememberAttributeColor("colorPrimary", mcTrack, themeId)
    val colorBackground = rememberAttributeColor("android:colorBackground", mcBg, themeId)
    val colorSurface = rememberAttributeColor("colorSurface", mcBg, themeId)

    // Build the ColorScheme dynamically from the library's XML theme attributes
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = colorPrimary,
            background = colorBackground,
            surface = colorSurface,
            onPrimary = Color.White,
            onBackground = Color.White,
            onSurface = Color.White
        )
    } else {
        lightColorScheme(
            primary = colorPrimary,
            background = colorBackground,
            surface = colorSurface,
            onPrimary = Color.White,
            onBackground = Color.Black,
            onSurface = Color.Black
        )
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = mcTrack.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    CompositionLocalProvider(
        LocalAppColors provides ExtraColors(
            primaryGradient = Brush.linearGradient(listOf(mcTrack, mcTrack2))
        )
    ) {
        MaterialTheme(
            colorScheme = colorScheme, typography = Typography, content = content
        )
    }
}