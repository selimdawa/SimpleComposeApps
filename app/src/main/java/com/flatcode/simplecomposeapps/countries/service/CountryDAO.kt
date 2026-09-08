package com.flatcode.simplecomposeapps.countries.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy
import com.flatcode.simplecomposeapps.countries.model.Country
import com.flatcode.simplecomposeapps.countries.model.CountrySettings
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDAO {

    @Insert
    suspend fun insertAll(vararg countries: Country): List<Long>

    @Query("SELECT * FROM country")
    suspend fun getAllCountries(): List<Country>

    @Query("SELECT * FROM country WHERE uuid = :countryId")
    suspend fun getCountry(countryId: Int): Country

    @Query("DELETE FROM country")
    suspend fun deleteAllCountries()

    @Query("SELECT refreshTime FROM country_settings WHERE id = 1")
    fun getRefreshTime(): Flow<Long?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSettings(settings: CountrySettings)
}