package com.flatcode.simplecomposeapps.todoNote.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.Locale

@Entity(tableName = "notes_table")
@Parcelize
data class Notes(
    val title: String,
    val content: String,
    val date: Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Parcelable {
    val dateCreatedFormatted: String
        get() = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(date)
}