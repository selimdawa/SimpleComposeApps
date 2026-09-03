package com.flatcode.simplecomposeapps.pdfreader.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import com.flatcode.simplecomposeapps.pdfreader.viewmodel.PdfViewModel
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.image_profile
import com.flatcode.simplecomposeapps.ui.theme.MC_TRACK
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.github.barteksc.pdfviewer.util.FitPolicy

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

    Scaffold(
        bottomBar = {
            if (uiState.isBottomBarVisible) {
                NavigationBar(
                    containerColor = COLOR_ON_BACKGROUND,
                ) {
                    NavigationBarItem(
                        selected = false, onClick = onPickFile, icon = {
                            Icon(
                                imageVector = AppIcons.FolderOpen,
                                contentDescription = Strings.PICK_FILE
                            )
                        }, colors = NavigationBarItemDefaults.colors(
                            unselectedIconColor = MC_TRACK,
                            selectedIconColor = MC_TRACK,
                            indicatorColor = Color.Transparent
                        )
                    )
                    NavigationBarItem(
                        selected = false, onClick = onMeta, icon = {
                            Icon(
                                imageVector = AppIcons.MetaInfo, contentDescription = Strings.META
                            )
                        }, colors = NavigationBarItemDefaults.colors(
                            unselectedIconColor = MC_TRACK,
                            selectedIconColor = MC_TRACK,
                            indicatorColor = Color.Transparent
                        )
                    )
                    NavigationBarItem(
                        selected = false, onClick = onShare, icon = {
                            Icon(
                                imageVector = AppIcons.Share,
                                contentDescription = Strings.SHARE_FILE
                            )
                        }, colors = NavigationBarItemDefaults.colors(
                            unselectedIconColor = MC_TRACK,
                            selectedIconColor = MC_TRACK,
                            indicatorColor = Color.Transparent
                        )
                    )
                    NavigationBarItem(
                        selected = false, onClick = onPrint, icon = {
                            Icon(
                                imageVector = AppIcons.Print, contentDescription = Strings.PRINT
                            )
                        }, colors = NavigationBarItemDefaults.colors(
                            unselectedIconColor = MC_TRACK,
                            selectedIconColor = MC_TRACK,
                            indicatorColor = Color.Transparent
                        )
                    )
                    NavigationBarItem(
                        selected = false, onClick = onFullscreen, icon = {
                            Icon(
                                imageVector = AppIcons.Fullscreen,
                                contentDescription = Strings.FULL_SCREEN
                            )
                        }, colors = NavigationBarItemDefaults.colors(
                            unselectedIconColor = MC_TRACK,
                            selectedIconColor = MC_TRACK,
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }, containerColor = image_profile // Whole screen image_profile
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(), color = MC_TRACK
                )
            }

            Box(modifier = Modifier.fillMaxSize()) {
                when {
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
                                    setBackgroundColor(image_profile.toArgb())
                                }
                            },
                            update = { pdfView ->
                                // Use a tag to avoid reloading the same URI
                                if (pdfView.tag != uiState.uri) {
                                    pdfView.tag = uiState.uri
                                    val configurator =
                                        if (uiState.uri?.scheme?.startsWith("http") == true) {
                                            uiState.pdfData?.let { pdfView.fromBytes(it) }
                                        } else {
                                            pdfView.fromUri(uiState.uri)
                                        }

                                    configurator?.apply {
                                        defaultPage(uiState.currentPage)
                                        onPageChange { page, pageCount ->
                                            viewModel.onPageChange(page, pageCount)
                                        }
                                        enableAnnotationRendering(true)
                                        onTap { _ ->
                                            viewModel.toggleBottomBar()
                                            true
                                        }
                                        scrollHandle(DefaultScrollHandle(pdfView.context))
                                        spacing(10)
                                        enableSwipe(true)
                                        swipeHorizontal(false)
                                        pageSnap(false) // Continuous scroll
                                        pageFling(true) // Momentum scroll
                                        autoSpacing(false)
                                        fitEachPage(false) // Normal continuous look
                                        pageFitPolicy(FitPolicy.WIDTH)
                                        enableDoubletap(true)
                                        enableAntialiasing(true)
                                        onError { t -> viewModel.onError(t) }
                                        load()
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    else -> {
                        if (!uiState.isLoading) {
                            Text(
                                text = Strings.PICK_FILE,
                                modifier = Modifier.align(Alignment.Center),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}