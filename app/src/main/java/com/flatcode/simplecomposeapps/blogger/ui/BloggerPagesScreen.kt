package com.flatcode.simplecomposeapps.blogger.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.flatcode.simplecomposeapps.blogger.model.Post
import com.flatcode.simplecomposeapps.blogger.viewmodel.BloggerViewModel
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.MC_TRACK
import com.flatcode.simplecomposeapps.ui.theme.Strings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloggerPagesScreen(
    viewModel: BloggerViewModel, onBack: () -> Unit, onPageClick: (String) -> Unit
) {
    val pages = viewModel.pages
    val isLoading = viewModel.isLoading.value

    LaunchedEffect(Unit) {
        viewModel.loadPages()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(
                        text = Strings.BLOGGER_PAGES,
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
            LazyColumn(
                modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(pages) { page ->
                    // Converting Page to Post for BloggerItem to reuse the Composable
                    val post = Post(
                        authorName = page.authorName,
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

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}