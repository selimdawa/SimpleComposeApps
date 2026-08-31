package com.flatcode.simplecomposeapps.blogger.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.flatcode.simplecomposeapps.blogger.viewmodel.BloggerViewModel
import com.flatcode.simplecomposeapps.ui.theme.Strings

@Composable
fun BloggerScreen(
    viewModel: BloggerViewModel,
    onBack: () -> Unit,
    onPagesClick: () -> Unit,
    onPostClick: (String) -> Unit
) {
    val posts = viewModel.posts
    val isLoading = viewModel.isLoading.value
    val hasMore = viewModel.hasMore

    LaunchedEffect(Unit) {
        if (posts.isEmpty()) {
            viewModel.loadPosts()
        }
    }

    Scaffold(
        topBar = {
            BloggerTopAppBar(
                title = Strings.BLOGGER_NAME,
                onSearch = { query -> viewModel.searchPosts(query) },
                onPagesClick = onPagesClick,
                onBack = onBack
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(posts) { post ->
                    BloggerItem(post = post, onClick = { post.id?.let { onPostClick(it) } })
                }

                if (hasMore && posts.isNotEmpty()) {
                    item {
                        Button(
                            onClick = { viewModel.loadPosts(isLoadMore = true) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text(text = Strings.LOAD_MORE, color = Color.White)
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
