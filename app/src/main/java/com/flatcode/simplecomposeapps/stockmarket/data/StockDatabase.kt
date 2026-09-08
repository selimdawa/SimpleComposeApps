package com.flatcode.simplecomposeapps.stockmarket.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CompanyListingEntity::class], version = 1, exportSchema = true)
abstract class StockDatabase : RoomDatabase() {
    abstract val dao: StockDao
}
