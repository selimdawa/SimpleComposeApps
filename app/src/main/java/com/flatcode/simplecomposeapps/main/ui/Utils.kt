package com.flatcode.simplecomposeapps.main.ui

import android.content.Context
import android.util.TypedValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

@Composable
@Suppress("DiscouragedApi")
fun rememberColorAttr(context: Context, attrName: String): Color {
    return remember(context, attrName) {
        val typedValue = TypedValue()
        var attributeResId = context.resources.getIdentifier(attrName, "attr", context.packageName)
        if (attributeResId == 0) {
            attributeResId =
                context.resources.getIdentifier(attrName, "attr", "com.flatcode.multicolors")
        }
        if (attributeResId != 0 && context.theme.resolveAttribute(
                attributeResId, typedValue, true
            )
        ) {
            Color(typedValue.data)
        } else {
            if (attrName.contains("track")) Color(0xFF6200EE) else Color.DarkGray
        }
    }
}