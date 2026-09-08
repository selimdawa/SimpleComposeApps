package com.flatcode.simplecomposeapps.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.flatcode.simplecomposeapps.ui.theme.AppIcons

@Composable
fun LoadingAnimation(
    modifier: Modifier = Modifier,
    color: Color? = null
) {
    val infiniteTransition = rememberInfiniteTransition(label = "loading")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing)
        ), label = "angle"
    )

    Image(
        imageVector = AppIcons.Loading,
        contentDescription = "Loading",
        colorFilter = color?.let { ColorFilter.tint(it) },
        modifier = modifier.rotate(angle)
    )
}

@Preview
@Composable
fun LoadingAnimationPreview() {
    LoadingAnimation(modifier = Modifier.size(100.dp))
}