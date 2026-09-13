package com.flatcode.simplecomposeapps.blogger.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.flatcode.simplecomposeapps.blogger.model.Post
import com.flatcode.simplecomposeapps.blogger.viewmodel.BloggerViewModel
import com.flatcode.simplecomposeapps.ui.CustomProgressBar
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.DATA
import com.flatcode.simplecomposeapps.utils.DATA.COLOR_ON_BACKGROUND

@Composable
fun BloggerPagesScreen(
    viewModel: BloggerViewModel, onBack: () -> Unit, onPageClick: (String) -> Unit
) {
    val pages by viewModel.pages.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadPages()
    }

    Scaffold(
        topBar = {
            Column(modifier = Modifier.padding(WindowInsets.statusBars.asPaddingValues())) {
                BloggerNameToolbar(
                    title = Strings.BLOGGER_PAGES,
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
            LazyColumn(
                modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 8.dp)
            ) {
                items(pages) { page ->
                    val post = Post(
                        author = page.author,
                        content = page.content,
                        id = page.id,
                        published = page.published,
                        selfLink = page.selfLink,
                        title = page.title,
                        updated = page.updated,
                        url = page.url
                    )
                    BloggerItem(post = post, onClick = { page.id?.let { onPageClick(it) } })
                }
            }

            if (isLoading && pages.isEmpty()) {
                CustomProgressBar(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            if (pages.isEmpty() && !isLoading && error != null) {
                Text(
                    text = Strings.FAILED_LOAD_DATA,
                    color = DATA.COLOR_ERROR,
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}