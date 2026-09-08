package com.flatcode.simplecomposeapps.web.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flatcode.simplecomposeapps.R
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.ui.theme.White

val CardTextSize = 18.sp
val CardCornerRadius = 10.dp
val SocialSize = 70.dp

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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 5.dp, end = 5.dp, bottom = 5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            WebItem(
                modifier = Modifier.weight(1f),
                cardModifier = Modifier
                    .fillMaxSize()
                    .padding(start = 5.dp, end = 5.dp, bottom = 5.dp),
                cornerRadius = CardCornerRadius,
                imageResId = AppIcons.Support,
                imageTint = White,
                imageSize = 80.dp,
                text = Strings.SUPPORT,
                textColor = White,
                textSize = CardTextSize,
                onClick = onSupport
            )

            WebItem(
                modifier = Modifier.weight(1f),
                cardModifier = Modifier
                    .fillMaxSize()
                    .padding(start = 5.dp, end = 5.dp, bottom = 5.dp),
                cornerRadius = CardCornerRadius,
                imageResId = AppIcons.AboutUs,
                imageTint = White,
                imageSize = 80.dp,
                text = Strings.ABOUT_US,
                textColor = White,
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
            WebItem(
                modifier = Modifier.weight(1f),
                cardModifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),
                cornerRadius = CardCornerRadius,
                imageResId = AppIcons.Website,
                imageTint = White,
                imageSize = 100.dp,
                text = Strings.WEB_SITE,
                textColor = White,
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
            WebItem(
                modifier = Modifier.weight(1f),
                cardModifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),
                cornerRadius = CardCornerRadius,
                imageResId = R.drawable.ic_share,
                imageTint = White,
                imageSize = 80.dp,
                text = Strings.SHARE_APP,
                textColor = White,
                textSize = CardTextSize,
                onClick = onShareApp
            )

            WebItem(
                modifier = Modifier.weight(1f),
                cardModifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),
                cornerRadius = CardCornerRadius,
                imageResId = AppIcons.Rate,
                imageTint = White,
                imageWidth = 120.dp,
                imageHeight = 80.dp,
                text = Strings.RATE_APP,
                textColor = White,
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
            SocialItem(
                modifier = Modifier.weight(1f), imageResId = AppIcons.Facebook, onClick = onFacebook
            )

            SocialItem(
                modifier = Modifier.weight(1f),
                imageResId = AppIcons.Instagram,
                onClick = onInstagram
            )

            SocialItem(
                modifier = Modifier
                    .weight(1f)
                    .scale(1.2f),
                imageResId = AppIcons.Twitter,
                onClick = onTwitter,
                padding = 0.dp
            )
        }
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