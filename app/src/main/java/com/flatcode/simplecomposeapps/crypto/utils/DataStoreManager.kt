package com.flatcode.simplecomposeapps.crypto.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "crypto_prefs")

class DataStoreManager(private val context: Context) {

    private val COIN_ID = intPreferencesKey("coin_id")
    private val COIN_SYMBOL = stringPreferencesKey("coin_symbol")

    suspend fun saveLastNav(id: Int, symbol: String) {
        context.dataStore.edit { preferences ->
            preferences[COIN_ID] = id
            preferences[COIN_SYMBOL] = symbol
        }
    }

    val lastCoinId: Flow<Int?> = context.dataStore.data.map { preferences ->
        preferences[COIN_ID]
    }

    val lastCoinSymbol: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[COIN_SYMBOL]
    }
}