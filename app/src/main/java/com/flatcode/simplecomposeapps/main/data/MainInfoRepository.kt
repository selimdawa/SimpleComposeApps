package com.flatcode.simplecomposeapps.main.data

import com.flatcode.simplecomposeapps.main.MainInfo
import com.flatcode.simplecomposeapps.utils.DATA
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainInfoRepository @Inject constructor() {
    suspend fun getInfoItems(): List<MainInfo> = withContext(Dispatchers.IO) {
        DATA.MAIN_INFO_DATA
    }
}