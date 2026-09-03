package com.flatcode.simplecomposeapps.blogger.ui

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.flatcode.simplecomposeapps.blogger.viewmodel.BloggerViewModel
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.MC_TRACK
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.DATA
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloggerDetailsScreen(
    viewModel: BloggerViewModel, id: String, isPage: Boolean = false, onBack: () -> Unit
) {
    val details = viewModel.details.value
    val labels = viewModel.labels
    val comments = viewModel.comments
    val isLoading = viewModel.isLoading.value
    val scrollState = rememberScrollState()

    val inputDateFormat = remember { SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH) }
    val outputDateFormat = remember { SimpleDateFormat("dd/MM/yyyy K:mm a", Locale.ENGLISH) }

    LaunchedEffect(id) {
        if (isPage) {
            viewModel.loadPageDetails(id)
        } else {
            viewModel.loadPostDetails(id)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(
                        text = if (isPage) Strings.PAGE_DETAILS else Strings.POST_DETAILS,
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }, navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = AppIcons.Back,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }, colors = TopAppBarDefaults.topAppBarColors(containerColor = MC_TRACK)
            )
        }, containerColor = COLOR_ON_BACKGROUND
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (details != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(16.dp)
                ) {
                    Text(
                        text = details.title ?: DATA.EMPTY,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    val formattedDate = try {
                        val date = inputDateFormat.parse(details.published ?: DATA.EMPTY)
                        if (date != null) outputDateFormat.format(date) else details.published
                            ?: DATA.EMPTY
                    } catch (_: Exception) {
                        details.published ?: DATA.EMPTY
                    }

                    Text(
                        text = Strings.publishInfo(details.authorName ?: DATA.EMPTY, formattedDate),
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    AndroidView(
                        factory = { context ->
                        WebView(context).apply {
                            webViewClient = WebViewClient()
                            settings.javaScriptEnabled = true
                            setBackgroundColor(0) // Transparent
                        }
                    },
                        update = { webView ->
                            webView.loadDataWithBaseURL(
                                null, details.content ?: "", "text/html", "UTF-8", null
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 200.dp) // Height might need to be dynamic
                    )

                    if (labels.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = Strings.LABELS,
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            items(labels) { label ->
                                BloggerLabelItem(label = label)
                            }
                        }
                    }

                    if (comments.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = Strings.COMMENTS,
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        comments.forEach { comment ->
                            BloggerCommentItem(comment = comment)
                        }
                    }
                }
            }

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
