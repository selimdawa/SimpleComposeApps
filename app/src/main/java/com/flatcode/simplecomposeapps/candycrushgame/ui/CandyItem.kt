package com.flatcode.simplecomposeapps.candycrushgame.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import kotlin.math.abs

enum class SwipeDirection {
    LEFT, RIGHT, TOP, BOTTOM
}

@Composable
fun CandyItem(
    candyResId: Int, onSwipe: (SwipeDirection) -> Unit
) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .pointerInput(Unit) {
                detectDragGestures(onDrag = { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }, onDragEnd = {
                    val threshold = 50f
                    if (abs(offsetX) > abs(offsetY)) {
                        if (abs(offsetX) > threshold) {
                            if (offsetX > 0) onSwipe(SwipeDirection.RIGHT)
                            else onSwipe(SwipeDirection.LEFT)
                        }
                    } else {
                        if (abs(offsetY) > threshold) {
                            if (offsetY > 0) onSwipe(SwipeDirection.BOTTOM)
                            else onSwipe(SwipeDirection.TOP)
                        }
                    }
                    offsetX = 0f
                    offsetY = 0f
                }, onDragCancel = {
                    offsetX = 0f
                    offsetY = 0f
                })
            }, contentAlignment = Alignment.Center
    ) {
        if (candyResId != -1) {
            Image(
                painter = painterResource(candyResId),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }
    }
}