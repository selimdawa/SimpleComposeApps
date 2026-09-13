package com.flatcode.simplecomposeapps.blogger.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flatcode.simplecomposeapps.blogger.viewmodel.BloggerViewModel
import com.flatcode.simplecomposeapps.ui.CustomProgressBar
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.ui.theme.White
import com.flatcode.simplecomposeapps.utils.DATA
import com.flatcode.simplecomposeapps.utils.DATA.COLOR_ERROR
import com.flatcode.simplecomposeapps.utils.DATA.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.utils.DATA.MC_BG

@Composable
fun BloggerScreen(
    viewModel: BloggerViewModel, onPagesClick: () -> Unit, onPostClick: (String) -> Unit
) {
    val posts by viewModel.posts.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    val error by viewModel.error.observeAsState()
    val hasMore = viewModel.hasMore

    var isSearchMode by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf(DATA.EMPTY) }

    val resetSearch = {
        isSearchMode = false
        searchQuery = DATA.EMPTY
        viewModel.loadPosts()
    }

    BackHandler(enabled = isSearchMode) {
        resetSearch()
    }

    LaunchedEffect(Unit) {
        if (posts.isEmpty()) {
            viewModel.loadPosts()
        }
    }

    val interactionSource = remember { MutableInteractionSource() }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier.padding(
                    top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
                    bottom = 0.dp
                )
            ) {
                BloggerSearchToolbar(
                    title = Strings.BLOGGER_NAME,
                    searchQuery = searchQuery,
                    onSearchQueryChange = {
                        searchQuery = it
                        viewModel.filterPosts(it)
                    },
                    isSearchMode = isSearchMode,
                    onSearchModeChange = { isSearchMode = it },
                    onPagesClick = onPagesClick,
                    onSearchExecute = { viewModel.searchPosts(searchQuery) })
            }
        }, containerColor = COLOR_ON_BACKGROUND
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(posts) { post ->
                        BloggerItem(post = post, onClick = { post.id?.let { onPostClick(it) } })
                    }

                    if (isLoading && posts.isNotEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CustomProgressBar(
                                    size = 24.dp
                                )
                            }
                        }
                    } else if (hasMore && posts.isNotEmpty()) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Card(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .clickable(
                                            interactionSource = interactionSource, indication = null
                                        ) {
                                            if (searchQuery.isEmpty()) viewModel.loadPosts(
                                                isLoadMore = true
                                            )
                                            else viewModel.searchPosts(
                                                searchQuery, isLoadMore = true
                                            )
                                        },
                                    shape = RoundedCornerShape(10.dp),
                                    elevation = CardDefaults.cardElevation(0.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .background(MC_BG)
                                            .padding(horizontal = 24.dp, vertical = 12.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = Strings.LOAD_MORE,
                                            color = White,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (isLoading && posts.isEmpty()) {
                CustomProgressBar(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            if (posts.isEmpty() && !isLoading && searchQuery.isNotEmpty()) {
                Text(
                    text = Strings.NO_DATA_FOUND,
                    color = COLOR_ERROR,
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            if (posts.isEmpty() && !isLoading && error != null) {
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