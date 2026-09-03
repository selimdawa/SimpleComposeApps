package com.flatcode.simplecomposeapps.wordpress.ui

import android.webkit.WebView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.AppTheme
import com.flatcode.simplecomposeapps.wordpress.utils.WordPressClient
import com.flatcode.simplecomposeapps.wordpress.utils.loadWordPressContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordpressDetailsScreen(
    id: Int,
    featuredMediaId: Int,
    title: String,
    content: String,
    isFavorite: Boolean,
    onBack: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    val scrollState = rememberScrollState()
    var mediaUrl by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(featuredMediaId) {
        if (featuredMediaId != -1) {
            try {
                val media = WordPressClient.apiService.getPostThumbnail(featuredMediaId)
                mediaUrl = media.guid?.rendered
            } catch (e: Exception) {
            }
        }
    }

    Scaffold(
        topBar = {
            ToolbarContentFav(
                title = title,
                showBack = true,
                showFavorite = true,
                isFavorite = isFavorite,
                onBackClick = onBack,
                onFavoriteClick = onToggleFavorite
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
            if (mediaUrl != null) {
                AsyncImage(
                    model = mediaUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))

                AndroidView(
                    factory = { context ->
                        WebView(context).apply {
                            loadWordPressContent(content)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 300.dp)
                )
            }
        }
    }
}
