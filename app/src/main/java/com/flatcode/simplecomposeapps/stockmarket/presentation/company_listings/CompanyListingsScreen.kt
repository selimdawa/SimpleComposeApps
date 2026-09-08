package com.flatcode.simplecomposeapps.stockmarket.presentation.company_listings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.flatcode.simplecomposeapps.ui.theme.Strings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyListingsScreen(
    viewModel: CompanyListingsViewModel = hiltViewModel(),
) {
    val state = viewModel.state
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Stock Market") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = { viewModel.onEvent(CompanyListingsEvent.Refresh) },
            modifier = Modifier.padding(paddingValues)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                OutlinedTextField(
                    value = state.searchQuery,
                    onValueChange = { viewModel.onEvent(CompanyListingsEvent.OnSearchQueryChange(it)) },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    placeholder = { Text(text = Strings.SEARCH) },
                    maxLines = 1,
                    singleLine = true,
                    leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
                    shape = MaterialTheme.shapes.large
                )
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(state.companies.size) { i ->
                        val company = state.companies[i]
                        CompanyItem(
                            company = company,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}