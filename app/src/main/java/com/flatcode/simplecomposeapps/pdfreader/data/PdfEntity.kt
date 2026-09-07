package com.flatcode.simplecomposeapps.pdfreader.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pdf_settings")
data class PdfEntity(
    @PrimaryKey val id: Int = 1,
    val lastUri: String?,
    val lastPage: Int
)