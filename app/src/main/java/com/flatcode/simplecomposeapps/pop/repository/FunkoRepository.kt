package com.flatcode.simplecomposeapps.pop.repository

import android.content.Context
import com.flatcode.simplecomposeapps.pop.db.PopDao
import com.flatcode.simplecomposeapps.pop.model.PopItem
import com.flatcode.simplecomposeapps.utils.DATA
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FunkoRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val popDao: PopDao
) {
    suspend fun loadPops() {
        withContext(Dispatchers.IO) {
            val jsonString = context.assets.open(DATA.FILE_POP).bufferedReader().use { it.readText() }
            val listType = object : TypeToken<List<PopItem>>() {}.type
            val pops: List<PopItem> = Gson().fromJson(jsonString, listType)
            popDao.deleteAllPops()
            popDao.insertPops(pops)
        }
    }

    fun getAllPops(): Flow<List<PopItem>> = popDao.getAllPops()
}