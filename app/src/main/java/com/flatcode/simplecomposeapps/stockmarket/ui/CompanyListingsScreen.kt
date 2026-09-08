package com.flatcode.simplecomposeapps.stockmarket.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.flatcode.simplecomposeapps.stockmarket.viewmodel.CompanyListingsViewModel
import com.flatcode.simplecomposeapps.ui.ToolbarContent
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ERROR
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.DATA

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyListingsScreen(
    viewModel: CompanyListingsViewModel = hiltViewModel(),
) {
    val state = viewModel.state

    Scaffold(
        topBar = {
            ToolbarContent(title = DATA.STOCK_MARKET)
        }, containerColor = COLOR_ON_BACKGROUND
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), color = COLOR_ON_BACKGROUND
        ) {
            PullToRefreshBox(
                isRefreshing = state.isRefreshing,
                onRefresh = { viewModel.onRefresh() },
                modifier = Modifier.fillMaxSize()
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    OutlinedTextField(
                        value = state.searchQuery,
                        onValueChange = {
                            viewModel.onSearchQueryChange(it)
                        },
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                            .fillMaxWidth(),
                        placeholder = {
                            Text(
                                text = Strings.SEARCH, style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        maxLines = 1,
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search, contentDescription = null
                            )
                        },
                        trailingIcon = {
                            if (state.searchQuery.isNotEmpty()) {
                                IconButton(onClick = {
                                    viewModel.onSearchQueryChange("")
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Clear, contentDescription = null
                                    )
                                }
                            }
                        },
                        shape = RoundedCornerShape(6.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = COLOR_ERROR,
                            unfocusedBorderColor = COLOR_ERROR.copy(alpha = 0.6f),
                            cursorColor = COLOR_ERROR,
                            focusedLeadingIconColor = COLOR_ERROR,
                            unfocusedLeadingIconColor = COLOR_ERROR.copy(alpha = 0.6f),
                            focusedTrailingIconColor = COLOR_ERROR,
                            unfocusedTrailingIconColor = COLOR_ERROR.copy(alpha = 0.6f),
                            focusedPlaceholderColor = COLOR_ERROR.copy(alpha = 0.6f),
                            unfocusedPlaceholderColor = COLOR_ERROR.copy(alpha = 0.4f),
                            focusedTextColor = COLOR_ERROR,
                            unfocusedTextColor = COLOR_ERROR,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            errorContainerColor = Color.Transparent
                        )
                    )
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        items(state.companies.size) { i ->
                            val company = state.companies[i]
                            CompanyItem(
                                company = company, modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}