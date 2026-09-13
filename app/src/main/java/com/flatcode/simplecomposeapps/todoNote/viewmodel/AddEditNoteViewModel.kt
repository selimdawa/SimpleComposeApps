package com.flatcode.simplecomposeapps.todoNote.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.todoNote.data.Notes
import com.flatcode.simplecomposeapps.todoNote.data.TodoRepository
import com.flatcode.simplecomposeapps.utils.DATA
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val repository: TodoRepository,
    state: SavedStateHandle
) : ViewModel() {

    val noteId = state.get<Int>("noteId") ?: -1
    val isEditMode = noteId != -1
    var note by mutableStateOf<Notes?>(null)
        private set

    private val _noteTitle = MutableStateFlow(state.get<String>("noteTitle") ?: "")
    val noteTitle: StateFlow<String> = _noteTitle.asStateFlow()

    private val _noteContent = MutableStateFlow(state.get<String>("noteContent") ?: "")
    val noteContent: StateFlow<String> = _noteContent.asStateFlow()

    init {
        if (noteId != -1) {
            viewModelScope.launch {
                note = repository.getNoteById(noteId)
                note?.let {
                    _noteTitle.value = it.title
                    _noteContent.value = it.content
                }
            }
        }
    }

    fun updateTitle(title: String) {
        _noteTitle.value = title
    }

    fun updateContent(content: String) {
        _noteContent.value = content
    }

    private val _addEditNoteEvent = MutableSharedFlow<AddEditNoteEvent>()
    val addEditNoteEvent: SharedFlow<AddEditNoteEvent> = _addEditNoteEvent

    fun onSaveClick() {
        val title = _noteTitle.value
        val content = _noteContent.value
        if (title.isBlank()) return

        val currentNote = note
        if (currentNote != null) {
            Timber.d("Updating existing note: %d", currentNote.id)
            val updatedNote = currentNote.copy(title = title, content = content)
            updateNote(updatedNote)
        } else {
            Timber.d("Creating new note")
            val newNote = Notes(title = title, content = content)
            createNote(newNote)
        }
    }

    private fun createNote(note: Notes) = viewModelScope.launch {
        repository.insertNote(note)
        _addEditNoteEvent.emit(AddEditNoteEvent.NavigateBackWithResult(DATA.ADD_RESULT_OK))
    }

    private fun updateNote(note: Notes) = viewModelScope.launch {
        repository.updateNote(note)
        _addEditNoteEvent.emit(AddEditNoteEvent.NavigateBackWithResult(DATA.EDIT_RESULT_OK))
    }

    sealed class AddEditNoteEvent {
        data class NavigateBackWithResult(val result: Int) : AddEditNoteEvent()
    }
}