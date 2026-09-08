package com.flatcode.simplecomposeapps.web.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ERROR

val SocialMarginHorizontal = 5.dp
val SocialPadding = 5.dp

@Composable
fun SocialItem(
    imageResId: Int, onClick: () -> Unit, modifier: Modifier = Modifier, padding: Dp = SocialPadding
) {
    Row(
        modifier = modifier
            .fillMaxHeight()
            .padding(horizontal = SocialMarginHorizontal)
            .clickable(
                interactionSource = remember { MutableInteractionSource() }, indication = null
            ) { onClick() }) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            colorFilter = ColorFilter.tint(COLOR_ERROR)
        )
    }
}