package com.flatcode.simplecomposeapps.multipledelete.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.flatcode.simplecomposeapps.multipledelete.di.MultiDeletePrefs
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MultiDeleteRepository @Inject constructor(
    private val multiDeleteDao: MultiDeleteDao,
    @MultiDeletePrefs private val sharedPreferences: SharedPreferences
) {
    fun getAllItems(): Flow<List<MultiDeleteEntity>> = multiDeleteDao.getAllItems()

    suspend fun insertInitialItems(items: List<String>) {
        val isFirstTime = sharedPreferences.getBoolean("is_first_time", true)
        if (isFirstTime) {
            multiDeleteDao.insertAll(items.map { MultiDeleteEntity(it) })
            sharedPreferences.edit { putBoolean("is_first_time", false) }
        }
    }

    suspend fun restoreItems(items: List<String>) {
        multiDeleteDao.deleteAll()
        multiDeleteDao.insertAll(items.map { MultiDeleteEntity(it) })
    }

    suspend fun deleteByTexts(texts: List<String>) {
        multiDeleteDao.deleteByTexts(texts)
    }
}
