package com.flatcode.simplecomposeapps.todoNote.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.ui.theme.White
import com.flatcode.simplecomposeapps.ui.theme.asapCondensed
import com.flatcode.simplecomposeapps.utils.DATA.MC_BG
import com.flatcode.simplecomposeapps.utils.DATA.MC_TRACK

@Composable
fun TodoUndoBar(
    isVisible: Boolean, message: String, modifier: Modifier = Modifier, onUndo: (() -> Unit)? = null
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { it }, animationSpec = tween(durationMillis = 300)
        ) + fadeIn(),
        exit = slideOutVertically(
            targetOffsetY = { it }, animationSpec = tween(durationMillis = 300)
        ) + fadeOut(),
        modifier = modifier
            .fillMaxWidth()
            .zIndex(1f)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MC_BG)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = message,
                    color = White,
                    fontSize = 16.sp,
                    fontFamily = asapCondensed,
                    modifier = Modifier.weight(1f)
                )

                onUndo?.let { undoAction ->
                    Button(
                        onClick = undoAction, colors = ButtonDefaults.buttonColors(
                            containerColor = White, contentColor = MC_TRACK
                        ), shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = Strings.UNDO,
                            fontWeight = FontWeight.Bold,
                            fontFamily = asapCondensed,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CenteredToast(
    isVisible: Boolean, message: String, modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(300)),
        modifier = modifier
            .zIndex(2f)
            .padding(16.dp)
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Text(
                text = message,
                color = White,
                fontSize = 14.sp,
                fontFamily = asapCondensed,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(MC_BG)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}