package com.flatcode.simplecomposeapps.web.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flatcode.simplecomposeapps.R
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.ToolbarContent
import com.flatcode.simplecomposeapps.ui.theme.AppTheme
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.ui.theme.rememberAttributeColor
import io.selimdawa.multicolors.MultiColorManager

@Composable
fun getMcBgColor(): Color {
    return AppTheme.colors.background
}

@Composable
fun getMcTickColor(): Color {
    val themeId by MultiColorManager.currentThemeId.collectAsState()
    return rememberAttributeColor("mc_track", MaterialTheme.colorScheme.primary, themeId)
}

val CardTextSize = 18.sp
val CardCornerRadius = 15.dp
val SocialSize = 70.dp
val SocialMarginHorizontal = 5.dp
val SocialPadding = 5.dp

@Composable
fun WebAppScreen(
    onWebSite: () -> Unit,
    onInstagram: () -> Unit,
    onTwitter: () -> Unit,
    onFacebook: () -> Unit,
    onAboutUs: () -> Unit,
    onSupport: () -> Unit,
    onShareApp: () -> Unit,
    onRateApp: () -> Unit
) {
    val themeId by MultiColorManager.currentThemeId.collectAsState()
    val colorOnBackground =
        rememberAttributeColor("colorOnBackground", MaterialTheme.colorScheme.onBackground, themeId)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorOnBackground)
    ) {
        ToolbarContent(
            title = Strings.WEB_APP, hasBack = false
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .padding(start = 5.dp, end = 5.dp, bottom = 5.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CardItem(
                    modifier = Modifier.weight(1f),
                    cardModifier = Modifier
                        .fillMaxSize()
                        .padding(start = 5.dp, end = 5.dp, bottom = 5.dp),
                    cornerRadius = CardCornerRadius,
                    imageResId = AppIcons.Support,
                    imageTint = Color.White,
                    imageSize = 80.dp,
                    text = Strings.SUPPORT,
                    textColor = Color.White,
                    textSize = CardTextSize,
                    onClick = onSupport
                )

                CardItem(
                    modifier = Modifier.weight(1f),
                    cardModifier = Modifier
                        .fillMaxSize()
                        .padding(start = 5.dp, end = 5.dp, bottom = 5.dp),
                    cornerRadius = CardCornerRadius,
                    imageResId = AppIcons.AboutUs,
                    imageTint = Color.White,
                    imageSize = 80.dp,
                    text = Strings.ABOUT_US,
                    textColor = Color.White,
                    textSize = CardTextSize,
                    onClick = onAboutUs
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CardItem(
                    modifier = Modifier.weight(1f),
                    cardModifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp),
                    cornerRadius = CardCornerRadius,
                    imageResId = AppIcons.Website,
                    imageTint = Color.White,
                    imageSize = 100.dp,
                    text = Strings.WEB_SITE,
                    textColor = Color.White,
                    textSize = 24.sp,
                    onClick = onWebSite
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CardItem(
                    modifier = Modifier.weight(1f),
                    cardModifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp),
                    cornerRadius = CardCornerRadius,
                    imageResId = R.drawable.ic_share,
                    imageTint = Color.White,
                    imageSize = 80.dp,
                    text = Strings.SHARE_APP,
                    textColor = Color.White,
                    textSize = CardTextSize,
                    onClick = onShareApp
                )

                CardItem(
                    modifier = Modifier.weight(1f),
                    cardModifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp),
                    cornerRadius = CardCornerRadius,
                    imageResId = AppIcons.Rate,
                    imageTint = Color.White,
                    imageWidth = 120.dp,
                    imageHeight = 80.dp,
                    text = Strings.RATE_APP,
                    textColor = Color.White,
                    textSize = CardTextSize,
                    onClick = onRateApp
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(SocialSize),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SocialIcon(
                    modifier = Modifier.weight(1f),
                    imageResId = AppIcons.Facebook,
                    onClick = onFacebook
                )

                SocialIcon(
                    modifier = Modifier.weight(1f),
                    imageResId = AppIcons.Instagram,
                    onClick = onInstagram
                )

                SocialIcon(
                    modifier = Modifier.weight(1f),
                    imageResId = AppIcons.Twitter,
                    onClick = onTwitter
                )
            }
        }
    }
}

@Composable
fun CardItem(
    modifier: Modifier = Modifier,
    cardModifier: Modifier = Modifier,
    cornerRadius: Dp,
    imageResId: Int,
    imageTint: Color,
    imageSize: Dp? = null,
    imageWidth: Dp? = null,
    imageHeight: Dp? = null,
    text: String,
    textColor: Color,
    textSize: TextUnit,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = cardModifier.clickable(
                interactionSource = remember { MutableInteractionSource() }, indication = null
            ) { onClick() },
            shape = RoundedCornerShape(cornerRadius),
            colors = CardDefaults.cardColors(containerColor = getMcBgColor())
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
                    modifier = Modifier.width(IntrinsicSize.Max),
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

@Composable
fun SocialIcon(
    modifier: Modifier = Modifier, imageResId: Int, onClick: () -> Unit
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
                .padding(SocialPadding),
            colorFilter = ColorFilter.tint(getMcTickColor())
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WebAppScreenPreview() {
    MaterialTheme {
        WebAppScreen(
            onWebSite = {},
            onInstagram = {},
            onTwitter = {},
            onFacebook = {},
            onAboutUs = {},
            onSupport = {},
            onShareApp = {},
            onRateApp = {})
    }
}