package com.flatcode.simplecomposeapps.ui.theme

import android.util.TypedValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.flatcode.simplecomposeapps.utils.DATA
import io.selimdawa.multicolors.MultiColorManager

@Composable
fun attr(name: String): Color {
    val themeId by MultiColorManager.currentThemeId.collectAsState()
    return rememberAttributeColor(name, Color.Unspecified, themeId)
}

val String.themeColor: Color
    @Composable get() = attr(this)

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