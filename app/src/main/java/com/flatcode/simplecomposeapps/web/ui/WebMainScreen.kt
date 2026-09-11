package com.flatcode.simplecomposeapps.web.ui

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.flatcode.simplecomposeapps.ui.theme.AppIcons
import com.flatcode.simplecomposeapps.utils.DATA.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.DATA
import com.flatcode.simplecomposeapps.web.activity.WebViewActivity
import com.flatcode.simplecomposeapps.web.viewmodel.WebAppViewModel

val CardTextSize = 18.sp
val CardCornerRadius = 15.dp
val SocialSize = 70.dp

@Composable
fun WebMainScreen(
    viewModel: WebAppViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(COLOR_ON_BACKGROUND)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 5.dp)
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
                    imageTint = Color.White,
                    imageSize = 80.dp,
                    text = Strings.SUPPORT,
                    textColor = Color.White,
                    textSize = CardTextSize,
                    onClick = { viewModel.showSupportDialog(true) })

                WebItem(
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
                    onClick = { viewModel.showAboutDialog(true) })
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
                    imageTint = Color.White,
                    imageSize = 100.dp,
                    text = Strings.WEB_SITE,
                    textColor = Color.White,
                    textSize = 24.sp,
                    onClick = {
                        val intent = Intent(context, WebViewActivity::class.java).apply {
                            putExtra("url", DATA.mySite)
                        }
                        context.startActivity(intent)
                    })
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
                    imageResId = AppIcons.ShareIcon,
                    imageTint = Color.White,
                    imageSize = 80.dp,
                    text = Strings.SHARE_APP,
                    textColor = Color.White,
                    textSize = CardTextSize,
                    onClick = { context.shareApp() })

                WebItem(
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
                    onClick = { context.rateApp() })
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(SocialSize),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SocialItem(
                    modifier = Modifier.weight(1f), imageResId = AppIcons.Facebook, onClick = {
                        val intent = Intent(context, WebViewActivity::class.java).apply {
                            putExtra("url", DATA.myFacebook)
                        }
                        context.startActivity(intent)
                    })

                SocialItem(
                    modifier = Modifier.weight(1f), imageResId = AppIcons.Instagram, onClick = {
                        val intent = Intent(context, WebViewActivity::class.java).apply {
                            putExtra("url", DATA.myInstagram)
                        }
                        context.startActivity(intent)
                    })

                SocialItem(
                    modifier = Modifier
                        .weight(1f)
                        .scale(1.2f),
                    imageResId = AppIcons.Twitter,
                    onClick = {
                        val intent = Intent(context, WebViewActivity::class.java).apply {
                            putExtra("url", DATA.myTwitter)
                        }
                        context.startActivity(intent)
                    },
                    padding = 0.dp
                )
            }
        }

        if (uiState.showAboutDialog) {
            WebAboutDialog(onDismiss = { viewModel.showAboutDialog(false) })
        }

        if (uiState.showSupportDialog) {
            WebSupportDialog(
                onDismiss = { viewModel.showSupportDialog(false) },
                onEmail = { context.sendEmail() },
                onPhone = { context.callPhone() })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WebMainScreenPreview() {
    WebMainScreen()
}

fun Context.shareApp() {
    val share = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(
            Intent.EXTRA_TEXT,
            "Share App with\nhttps://play.google.com/store/apps/details?id=$packageName"
        )
    }
    startActivity(Intent.createChooser(share, "Share link!"))
}

fun Context.rateApp() {
    val uri = "market://details?id=$packageName".toUri()
    val intent = Intent(Intent.ACTION_VIEW, uri)
    try {
        startActivity(intent)
    } catch (_: Exception) {
        startActivity(Intent(Intent.ACTION_VIEW, "http://google.com".toUri()))
    }
}

fun Context.sendEmail() {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = "mailto:${DATA.myEmail}".toUri()
    }
    startActivity(intent)
}

fun Context.callPhone() {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = "tel:${DATA.myMobileNumber}".toUri()
    }
    startActivity(intent)
}