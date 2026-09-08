package com.flatcode.simplecomposeapps.todoNote.viewmodel

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
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.flatcode.simplecomposeapps.utils.DATA

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteDao: NoteDao,
    state: SavedStateHandle
) : ViewModel() {

    val note = state.get<Notes>("note")

    var noteTitle by mutableStateOf(state.get<String>("noteTitle") ?: note?.title ?: "")
    var noteContent by mutableStateOf(state.get<String>("noteContent") ?: note?.content ?: "")

    private val _addEditNoteEvent = MutableSharedFlow<AddEditNoteEvent>()
    val addEditNoteEvent: SharedFlow<AddEditNoteEvent> = _addEditNoteEvent

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
        _addEditNoteEvent.emit(AddEditNoteEvent.NavigateBackWithResult(DATA.ADD_RESULT_OK))
    }

    private fun updateNote(note: Notes) = viewModelScope.launch {
        noteDao.update(note)
        _addEditNoteEvent.emit(AddEditNoteEvent.NavigateBackWithResult(DATA.EDIT_RESULT_OK))
    }

    sealed class AddEditNoteEvent {
        data class NavigateBackWithResult(val result: Int) : AddEditNoteEvent()
    }
}