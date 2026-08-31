package com.flatcode.simplecomposeapps.newsapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.flatcode.simplecomposeapps.newsapp.model.NewsHeadlines
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.AppTheme
import com.flatcode.simplecomposeapps.utils.DATA

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsAppDetailsScreen(
    headline: NewsHeadlines,
    onBack: () -> Unit
) {
    val mcTrack = AppTheme.colors.track
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "News Details",
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
        ) {
            AsyncImage(
                model = headline.urlToImage,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = headline.title ?: DATA.EMPTY,
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = headline.author ?: "Unknown Author",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                    Text(
                        text = headline.publishedAt ?: DATA.EMPTY,
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = Color.White.copy(alpha = 0.2f))
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = headline.description ?: DATA.EMPTY,
                    color = Color.White,
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = headline.content ?: DATA.EMPTY,
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                )
            }
        }
    }
}
