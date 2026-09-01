package com.flatcode.simplecomposeapps.stockmarket.domain.repository

import com.flatcode.simplecomposeapps.stockmarket.domain.model.CompanyListing
import com.flatcode.simplecomposeapps.stockmarket.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>
}