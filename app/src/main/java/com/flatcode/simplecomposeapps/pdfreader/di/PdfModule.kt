package com.flatcode.simplecomposeapps.pdfreader.di

import android.content.Context
import androidx.room.Room
import com.flatcode.simplecomposeapps.pdfreader.data.PdfDao
import com.flatcode.simplecomposeapps.pdfreader.data.PdfDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PdfModule {

    @Provides
    @Singleton
    fun providePdfDatabase(@ApplicationContext context: Context): PdfDatabase {
        return Room.databaseBuilder(
            context,
            PdfDatabase::class.java,
            "pdf_database"
        ).build()
    }

    @Provides
    fun providePdfDao(database: PdfDatabase): PdfDao {
        return database.pdfDao()
    }
}