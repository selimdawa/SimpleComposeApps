package com.flatcode.simplecomposeapps.ui.theme

import android.app.Activity
import android.util.TypedValue
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
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
import com.flatcode.simplecomposeapps.utils.DATA
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
fun attr(name: String): Color {
    val themeId by MultiColorManager.currentThemeId.collectAsState()
    return rememberAttributeColor(name, Color.Unspecified, themeId)
}

val String.themeColor: Color
    @Composable
    get() = attr(this)

// Shortcuts for Theme Colors
val COLOR_ERROR: Color @Composable get() = DATA.COLOR_ERROR.themeColor
val COLOR_ON_BACKGROUND: Color @Composable get() = DATA.COLOR_ON_BACKGROUND.themeColor
val MC_BG: Color @Composable get() = DATA.MC_BG.themeColor
val MC_TRACK: Color @Composable get() = DATA.MC_TRACK.themeColor
val MC_TICK: Color @Composable get() = DATA.MC_TICK.themeColor

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

    // Fetch gradient colors from the selected theme (e.g., G2 themes)
    val mcTrack = rememberAttributeColor(DATA.MC_TRACK, McTrackColor, themeId)
    val mcTick = rememberAttributeColor(DATA.MC_TICK, mcTrack, themeId)

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
            primaryGradient = Brush.linearGradient(listOf(mcTrack, mcTick))
        )
    ) {
        MaterialTheme(
            typography = Typography, content = content
        )
    }
}