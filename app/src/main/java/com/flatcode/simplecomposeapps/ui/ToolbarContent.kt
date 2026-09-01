package com.flatcode.simplecomposeapps.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flatcode.simplecomposeapps.ui.theme.AppTheme

@Composable
fun ToolbarContent(
    title: String,
    leftIcon: ImageVector? = null,
    onLeftClick: (() -> Unit)? = null,
    rightIcon: ImageVector? = null,
    onRightClick: (() -> Unit)? = null,
    hasBack: Boolean = false,
    onBackClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable (() -> Unit)? = null
) {
    val mcBgColor = AppTheme.colors.background

    Card(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(10.dp),
        shape = RoundedCornerShape(6.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        colors = CardDefaults.cardColors(containerColor = mcBgColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
        ) {
            val finalNavigationIcon: @Composable (() -> Unit)? = navigationIcon ?: if (hasBack) {
                {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clickable(enabled = onBackClick != null) { onBackClick?.invoke() }
                    ) {
                        Icon(
                            imageVector = AppIcons.Back,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            } else null

            if (finalNavigationIcon != null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 10.dp)
                ) {
                    finalNavigationIcon()
                }
            } else if (leftIcon != null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 10.dp)
                        .size(30.dp)
                        .clickable(enabled = onLeftClick != null) { onLeftClick?.invoke() }) {
                    Icon(
                        imageVector = leftIcon,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Text(
                text = title,
                modifier = Modifier.align(Alignment.Center),
                color = Color.White,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            if (actions != null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 10.dp)
                ) {
                    actions()
                }
            } else if (rightIcon != null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 15.dp)
                        .size(30.dp)
                        .clickable(enabled = onRightClick != null) { onRightClick?.invoke() }) {
                    Icon(
                        imageVector = rightIcon,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}