package com.flatcode.simplecomposeapps.stockmarket.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.flatcode.simplecomposeapps.stockmarket.model.CompanyListing

@Entity
data class CompanyListingEntity(
    val name: String,
    val symbol: String,
    val exchange: String,
    @PrimaryKey val id: Int? = null
)

fun CompanyListingEntity.toCompanyListing(): CompanyListing {
    return CompanyListing(name = name, symbol = symbol, exchange = exchange)
}

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity {
    return CompanyListingEntity(name = name, symbol = symbol, exchange = exchange)
}
