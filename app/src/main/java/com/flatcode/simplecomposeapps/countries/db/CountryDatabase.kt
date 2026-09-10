package com.flatcode.simplecomposeapps.countries.db

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.flatcode.simplecomposeapps.countries.model.Country
import com.flatcode.simplecomposeapps.countries.model.CountrySettings

@Database(
    entities = [Country::class, CountrySettings::class],
    version = 2,
    autoMigrations = [AutoMigration(from = 1, to = 2)],
    exportSchema = true
)
abstract class CountryDatabase : RoomDatabase() {

    abstract fun countryDao(): CountryDAO

    companion object {

        @Volatile
        private var instance: CountryDatabase? = null
        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: makeDatabase(context).also { instance = it }
        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, CountryDatabase::class.java, "countries_db"
        ).build()
    }
}