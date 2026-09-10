package com.flatcode.simplecomposeapps.countries.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import com.flatcode.simplecomposeapps.countries.model.Country
import com.flatcode.simplecomposeapps.countries.model.CountrySettings

@Dao
interface CountryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg countries: Country): List<Long>

    @Transaction
    suspend fun refreshCountries(vararg countries: Country): List<Long> {
        deleteAllCountries()
        return insertAll(*countries)
    }

    @Query("SELECT * FROM Country")
    suspend fun getAllCountries(): List<Country>

    @Query("SELECT * FROM Country WHERE uuid = :countryId")
    suspend fun getCountry(countryId: Int): Country

    @Query("DELETE FROM Country")
    suspend fun deleteAllCountries()

    @Query("SELECT refreshTime FROM country_settings WHERE id = 1")
    suspend fun getRefreshTime(): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSettings(settings: CountrySettings)
}