package com.flatcode.simplecomposeapps.todoNote.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Notes)

    @Update
    suspend fun update(note: Notes)

    @Delete
    suspend fun delete(note: Notes)

    @Query("SELECT * FROM notes_table ORDER BY date DESC")
    fun getAllNotes(): Flow<List<Notes>>

    @Query("DELETE FROM notes_table")
    suspend fun deleteAllNotes()
}