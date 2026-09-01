package com.flatcode.simplecomposeapps.stockmarket.presentation.company_listings

import com.flatcode.simplecomposeapps.stockmarket.domain.model.CompanyListing

data class CompanyListingsState(
    val companies: List<CompanyListing> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
)