package com.flatcode.simplecomposeapps.stockmarket.model

data class CompanyListingsState(
    val companies: List<CompanyListing> = emptyList(),
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
)