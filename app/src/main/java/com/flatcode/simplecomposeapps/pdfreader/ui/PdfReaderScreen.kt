package com.flatcode.simplecomposeapps.pdfreader.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import com.flatcode.simplecomposeapps.pdfreader.viewmodel.PdfViewModel
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.AppTheme
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PdfReaderScreen(
    viewModel: PdfViewModel,
    onPickFile: () -> Unit,
    onMeta: () -> Unit,
    onShare: () -> Unit,
    onPrint: () -> Unit,
    onFullscreen: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val mcTrack = AppTheme.colors.track

    Scaffold(
        bottomBar = {
            if (uiState.isBottomBarVisible) {
                BottomAppBar(
                    containerColor = mcTrack,
                    actions = {
                        IconButton(onClick = onPickFile) {
                            Icon(imageVector = AppIcons.FolderOpen, contentDescription = "Pick File", tint = Color.White)
                        }
                        IconButton(onClick = onMeta) {
                            Icon(imageVector = AppIcons.Info, contentDescription = "Meta", tint = Color.White)
                        }
                        IconButton(onClick = onShare) {
                            Icon(imageVector = AppIcons.Share, contentDescription = "Share", tint = Color.White)
                        }
                        IconButton(onClick = onPrint) {
                            Icon(imageVector = AppIcons.Print, contentDescription = "Print", tint = Color.White)
                        }
                        IconButton(onClick = onFullscreen) {
                            Icon(imageVector = AppIcons.Fullscreen, contentDescription = "Fullscreen", tint = Color.White)
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                uiState.errorMessage != null -> {
                    Text(
                        text = uiState.errorMessage!!,
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.error
                    )
                }
                uiState.uri != null -> {
                    AndroidView(
                        factory = { context ->
                            PDFView(context, null).apply {
                                setBackgroundColor(android.graphics.Color.LTGRAY)
                            }
                        },
                        update = { pdfView ->
                            val configurator = if (uiState.uri?.scheme?.startsWith("http") == true) {
                                uiState.pdfData?.let { pdfView.fromBytes(it) }
                            } else {
                                pdfView.fromUri(uiState.uri)
                            }

                            configurator?.apply {
                                defaultPage(uiState.currentPage)
                                onPageChange { page, pageCount -> viewModel.onPageChange(page, pageCount) }
                                enableAnnotationRendering(true)
                                onTap { _ ->
                                    viewModel.toggleBottomBar()
                                    true
                                }
                                scrollHandle(DefaultScrollHandle(pdfView.context))
                                spacing(10)
                                onError { t -> viewModel.onError(t) }
                                load()
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                else -> {
                    Text(
                        text = "Pick a PDF file to read",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
