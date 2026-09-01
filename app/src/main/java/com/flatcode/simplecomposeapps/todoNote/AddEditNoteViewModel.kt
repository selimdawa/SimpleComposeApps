package com.flatcode.simplecomposeapps.todoNote

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.todoNote.data.NoteDao
import com.flatcode.simplecomposeapps.todoNote.data.Notes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteDao: NoteDao,
    private val state: SavedStateHandle
) : ViewModel() {

    val note = state.get<Notes>("note")

    var noteTitle by mutableStateOf(state.get<String>("noteTitle") ?: note?.title ?: "")
    var noteContent by mutableStateOf(state.get<String>("noteContent") ?: note?.content ?: "")

    private val _addEditNoteEvent = MutableSharedFlow<AddEditNoteEvent>()
    val addEditNoteEvent = _addEditNoteEvent.asSharedFlow()

    fun onSaveClick() {
        if (noteTitle.isBlank()) return

        if (note != null) {
            val updatedNote = note.copy(title = noteTitle, content = noteContent)
            updateNote(updatedNote)
        } else {
            val newNote = Notes(title = noteTitle, content = noteContent)
            createNote(newNote)
        }
    }

    private fun createNote(note: Notes) = viewModelScope.launch {
        noteDao.insert(note)
        _addEditNoteEvent.emit(AddEditNoteEvent.NavigateBackWithResult)
    }

    private fun updateNote(note: Notes) = viewModelScope.launch {
        noteDao.update(note)
        _addEditNoteEvent.emit(AddEditNoteEvent.NavigateBackWithResult)
    }

    sealed class AddEditNoteEvent {
        data object NavigateBackWithResult : AddEditNoteEvent()
    }
}