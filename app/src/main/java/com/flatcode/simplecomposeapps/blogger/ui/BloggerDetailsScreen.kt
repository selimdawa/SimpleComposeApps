package com.flatcode.simplecomposeapps.blogger.ui

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.flatcode.simplecomposeapps.blogger.viewmodel.BloggerViewModel
import com.flatcode.simplecomposeapps.ui.CustomProgressBar
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.DATA
import com.flatcode.simplecomposeapps.utils.DATA.COLOR_ERROR
import com.flatcode.simplecomposeapps.utils.DATA.COLOR_ON_BACKGROUND
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun BloggerDetailsScreen(
    viewModel: BloggerViewModel, id: String, isPage: Boolean = false, onBack: () -> Unit
) {
    val details by viewModel.details.collectAsStateWithLifecycle()
    val labels by viewModel.labels.collectAsStateWithLifecycle()
    val comments by viewModel.comments.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    val errorColor = COLOR_ERROR
    val errorHex = remember(errorColor) {
        String.format("#%06X", 0xFFFFFF and errorColor.toArgb())
    }

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
            Column(modifier = Modifier.padding(WindowInsets.statusBars.asPaddingValues())) {
                BloggerNameToolbar(
                    title = if (isPage) Strings.PAGE_DETAILS else Strings.POST_DETAILS,
                    onBack = onBack
                )
            }
        }, containerColor = COLOR_ON_BACKGROUND
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            details?.let { item ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(16.dp)
                ) {
                    Text(
                        text = item.title ?: DATA.EMPTY,
                        color = COLOR_ERROR,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    val formattedDate = try {
                        val date = inputDateFormat.parse(item.published ?: DATA.EMPTY)
                        if (date != null) outputDateFormat.format(date) else item.published
                            ?: DATA.EMPTY
                    } catch (_: Exception) {
                        item.published ?: DATA.EMPTY
                    }

                    Text(
                        text = Strings.publishInfo(item.authorName ?: DATA.EMPTY, formattedDate),
                        color = COLOR_ERROR,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    AndroidView(
                        factory = { context ->
                        WebView(context).apply {
                            webViewClient = WebViewClient()
                            settings.javaScriptEnabled = false
                            setBackgroundColor(0)
                        }
                    }, update = { webView ->
                        val customHtml = """
                            <html>
                            <head>
                            <style>
                            body { color: $errorHex; background-color: transparent; }
                            img { max-width: 100%; height: auto; }
                            </style>
                            </head>
                            <body>
                            ${item.content ?: ""}
                            </body>
                            </html>
                        """.trimIndent()
                        webView.loadDataWithBaseURL(
                            null, customHtml, "text/html", "UTF-8", null
                        )
                    }, modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 200.dp)
                    )

                    if (labels.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = Strings.LABELS,
                            color = COLOR_ERROR,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
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
                            color = COLOR_ERROR,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        comments.forEach { comment ->
                            BloggerCommentItem(comment = comment)
                        }
                    }
                }
            }

            if (isLoading && details == null) {
                CustomProgressBar(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            if (details == null && !isLoading && error != null) {
                Text(
                    text = Strings.FAILED_LOAD_DATA,
                    color = COLOR_ERROR,
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}