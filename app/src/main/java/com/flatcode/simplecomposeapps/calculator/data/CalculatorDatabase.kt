package com.flatcode.simplecomposeapps.calculator.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CalculatorEntity::class], version = 1, exportSchema = false)
abstract class CalculatorDatabase : RoomDatabase() {
    abstract fun calculatorDao(): CalculatorDao
}