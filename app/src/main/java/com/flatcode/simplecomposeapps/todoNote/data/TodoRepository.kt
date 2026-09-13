package com.flatcode.simplecomposeapps.todoNote.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoRepository @Inject constructor(
    private val noteDao: NoteDao,
    private val taskDao: TaskDao,
    private val preferencesManager: PreferencesManager
) {
    // Notes
    fun getNotes(query: String, sortOrder: SortOrder): Flow<List<Notes>> =
        noteDao.getNotes(query, sortOrder)

    suspend fun getNoteById(id: Int): Notes? = noteDao.getNoteById(id)

    suspend fun insertNote(note: Notes) = noteDao.insert(note)

    suspend fun insertNotes(notes: List<Notes>) = noteDao.insertAll(notes)

    suspend fun updateNote(note: Notes) = noteDao.update(note)

    suspend fun deleteNote(note: Notes) = noteDao.delete(note)

    suspend fun getAllNotesList(): List<Notes> = noteDao.getAllNotesList()

    suspend fun deleteAllNotes() = noteDao.deleteAllNotes()

    val notesPreferencesFlow = preferencesManager.notesPreferencesFlow

    suspend fun updateSortOrderNotes(sortOrder: SortOrder) =
        preferencesManager.updateSortOrderNotes(sortOrder)

    // Tasks
    fun getTasks(query: String, sortOrder: SortOrder, hideCompleted: Boolean): Flow<List<Task>> =
        taskDao.getTasks(query, sortOrder, hideCompleted)

    suspend fun getTaskById(id: Int): Task? = taskDao.getTaskById(id)

    suspend fun insertTask(task: Task) = taskDao.insert(task)

    suspend fun insertTasks(tasks: List<Task>) = taskDao.insertAll(tasks)

    suspend fun updateTask(task: Task) = taskDao.update(task)

    suspend fun deleteTask(task: Task) = taskDao.delete(task)

    suspend fun deleteCompletedTasks() = taskDao.deleteCompletedTasks()

    suspend fun getCompletedTasksList(): List<Task> = taskDao.getCompletedTasksList()

    val tasksPreferencesFlow = preferencesManager.preferencesFlow

    suspend fun updateSortOrderTasks(sortOrder: SortOrder) =
        preferencesManager.updateSortOrder(sortOrder)

    suspend fun updateHideCompleted(hideCompleted: Boolean) =
        preferencesManager.updateHideCompleted(hideCompleted)
}
