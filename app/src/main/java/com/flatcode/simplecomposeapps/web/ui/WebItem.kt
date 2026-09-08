package com.flatcode.simplecomposeapps.web.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ERROR
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND

@Composable
fun WebItem(
    cornerRadius: Dp,
    imageResId: Int,
    imageTint: Color,
    text: String,
    textColor: Color,
    textSize: TextUnit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    cardModifier: Modifier = Modifier,
    imageSize: Dp? = null,
    imageWidth: Dp? = null,
    imageHeight: Dp? = null
) {
    Column(
        modifier = modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = cardModifier.clickable { onClick() },
            shape = RoundedCornerShape(cornerRadius),
            colors = CardDefaults.cardColors(containerColor = COLOR_ON_BACKGROUND)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = null,
                    modifier = when {
                        imageSize != null -> Modifier.size(imageSize)
                        (imageWidth != null && imageHeight != null) -> Modifier
                            .width(imageWidth)
                            .height(imageHeight)

                        else -> Modifier.size(80.dp)
                    },
                    colorFilter = ColorFilter.tint(imageTint)
                )

                Text(
                    text = text,
                    modifier = Modifier
                        .width(IntrinsicSize.Max)
                        .padding(top = 5.dp),
                    textAlign = TextAlign.Center,
                    color = textColor,
                    fontSize = textSize,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}