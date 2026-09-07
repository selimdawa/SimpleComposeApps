package com.flatcode.simplecomposeapps.pdfreader.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PdfDao {
    @Query("SELECT * FROM pdf_settings WHERE id = 1")
    fun getSettings(): Flow<PdfEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSettings(settings: PdfEntity)
}