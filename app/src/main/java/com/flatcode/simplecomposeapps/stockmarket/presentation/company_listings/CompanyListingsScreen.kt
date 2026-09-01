package com.flatcode.simplecomposeapps.stockmarket.presentation.company_listings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph

@Composable
@Destination<RootGraph>(start = true)
fun CompanyListingsScreen(
    viewModel: CompanyListingsViewModel = hiltViewModel(),
) {
    val state = viewModel.state
    PullToRefreshBox(
        isRefreshing = state.isRefreshing,
        onRefresh = { viewModel.onEvent(CompanyListingsEvent.Refresh) }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = { viewModel.onEvent(CompanyListingsEvent.OnSearchQueryChange(it)) },
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                placeholder = { Text(text = "Search") },
                maxLines = 1,
                singleLine = true,
                leadingIcon = { Icon(imageVector = Icons.Outlined.Search, contentDescription = null) }
            )
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.companies.size) { i ->
                    val company = state.companies[i]
                    CompanyItem(
                        company = company,
                        modifier = Modifier.fillMaxWidth().padding(16.dp)
                    )
                    if (i < state.companies.size - 1) {
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                    }
                }
            }
        }
    }
}