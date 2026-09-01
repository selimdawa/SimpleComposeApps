package com.flatcode.simplecomposeapps.stockmarket.data.mapper

import com.flatcode.simplecomposeapps.stockmarket.data.local.CompanyListingEntity
import com.flatcode.simplecomposeapps.stockmarket.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing {
    return CompanyListing(name = name, symbol = symbol, exchange = exchange)
}

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity {
    return CompanyListingEntity(name = name, symbol = symbol, exchange = exchange)
}