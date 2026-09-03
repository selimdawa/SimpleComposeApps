package com.flatcode.simplecomposeapps.blogger.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.flatcode.simplecomposeapps.blogger.viewmodel.BloggerViewModel
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
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
        }, containerColor = COLOR_ON_BACKGROUND
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 16.dp)
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