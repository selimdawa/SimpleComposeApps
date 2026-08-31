package com.flatcode.simplecomposeapps.webapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flatcode.simplecomposeapps.R
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.AppTheme
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.DATA

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebAppScreen(
    onBack: () -> Unit,
    onWebSite: () -> Unit,
    onInstagram: () -> Unit,
    onTwitter: () -> Unit,
    onFacebook: () -> Unit,
    onAboutUs: () -> Unit,
    onSupport: () -> Unit,
    onShareApp: () -> Unit,
    onRateApp: () -> Unit
) {
    val mcTrack = AppTheme.colors.track
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = Strings.WEB_APP,
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = AppIcons.Back, contentDescription = null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = mcTrack)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                WebMenuIcon(icon = AppIcons.Website, label = Strings.WEB_SITE, onClick = onWebSite)
                WebMenuIcon(icon = AppIcons.Instagram, label = "Instagram", onClick = onInstagram)
                WebMenuIcon(icon = AppIcons.Twitter, label = "Twitter", onClick = onTwitter)
                WebMenuIcon(icon = AppIcons.Facebook, label = "Facebook", onClick = onFacebook)
            }

            Spacer(modifier = Modifier.height(24.dp))

            WebMenuButton(label = Strings.ABOUT_US, onClick = onAboutUs)
            WebMenuButton(label = Strings.SUPPORT, onClick = onSupport)
            WebMenuButton(label = Strings.SHARE_APP, onClick = onShareApp)
            WebMenuButton(label = Strings.RATE_APP, onClick = onRateApp)
        }
    }
}

@Composable
fun WebMenuIcon(icon: Any, label: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Surface(
            shape = CircleShape,
            color = AppTheme.colors.track,
            modifier = Modifier.size(60.dp)
        ) {
            when (icon) {
                is Int -> Image(
                    painter = painterResource(id = icon),
                    contentDescription = label,
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxSize()
                )
                is ImageVector -> Icon(
                    imageVector = icon,
                    contentDescription = label,
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxSize(),
                    tint = Color.Unspecified
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = label, color = Color.White, fontSize = 12.sp)
    }
}

@Composable
fun WebMenuButton(label: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.colors.track)
    ) {
        Text(text = label, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun WebAboutDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = Strings.ABOUT_US) },
        text = { Text(text = DATA.aboutUs) },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(Strings.OK)
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        textContentColor = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
fun WebSupportDialog(
    onDismiss: () -> Unit,
    onEmail: () -> Unit,
    onPhone: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = Strings.SUPPORT) },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(onClick = onEmail, modifier = Modifier.size(60.dp)) {
                        Icon(painter = painterResource(AppIcons.Email), contentDescription = "Email", modifier = Modifier.size(40.dp), tint = Color.Unspecified)
                    }
                    IconButton(onClick = onPhone, modifier = Modifier.size(60.dp)) {
                        Icon(painter = painterResource(AppIcons.Phone), contentDescription = "Phone", modifier = Modifier.size(40.dp), tint = Color.Unspecified)
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(Strings.CANCEL)
            }
        }
    )
}
