package com.flatcode.simplecomposeapps.web.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebRepository @Inject constructor(
    private val webDao: WebDao
) {
    fun getItemsByType(type: String): Flow<List<WebEntity>> = webDao.getItemsByType(type)

    suspend fun insertItem(item: WebEntity) {
        webDao.insertItem(item)
    }

    suspend fun deleteItem(item: WebEntity) {
        webDao.deleteItem(item)
    }

    suspend fun clearByType(type: String) {
        webDao.clearByType(type)
    }
}
