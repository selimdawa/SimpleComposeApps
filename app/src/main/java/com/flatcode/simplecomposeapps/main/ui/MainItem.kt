package com.flatcode.simplecomposeapps.main.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flatcode.simplecomposeapps.main.Main
import com.flatcode.simplecomposeapps.ui.theme.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.AppTheme
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.openActivity

@Composable
fun MainItem(item: Main) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val gradientBrush = AppTheme.colors.primaryGradient

    Card(
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { item.c?.let { context.openActivity(it) } },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier
                .background(gradientBrush)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .background(Color.White, CircleShape)
                        .align(Alignment.CenterStart), contentAlignment = Alignment.Center
                ) {
                    val image = item.image ?: AppIcons.Load
                    when (image) {
                        is ImageVector -> {
                            Image(
                                imageVector = image,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                contentScale = ContentScale.Crop,
                                colorFilter = ColorFilter.tint(Color.Black)
                            )
                        }
                        is Int -> {
                            Image(
                                painter = painterResource(id = image),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                contentScale = ContentScale.Crop,
                                colorFilter = ColorFilter.tint(Color.Black)
                            )
                        }
                    }
                }

                if (item.number != 0) {
                    Text(
                        text = Strings.numberPlaceholder(item.number),
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .height(30.dp)
                            .align(Alignment.TopEnd)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = item.title ?: "",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Start
            )
        }
    }
}
