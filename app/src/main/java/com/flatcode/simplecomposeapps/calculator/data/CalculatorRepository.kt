package com.flatcode.simplecomposeapps.calculator.data

import kotlinx.coroutines.flow.Flow

class CalculatorRepository(
    private val calculatorDao: CalculatorDao
) {
    fun getAllHistory(): Flow<List<CalculatorEntity>> = calculatorDao.getAllHistory()

    suspend fun insertHistory(history: CalculatorEntity) {
        calculatorDao.insertHistory(history)
    }

    suspend fun clearHistory() {
        calculatorDao.clearHistory()
    }
}