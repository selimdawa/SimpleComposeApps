package com.flatcode.simplecomposeapps.todoNote.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.todoNote.data.Task
import com.flatcode.simplecomposeapps.todoNote.data.TodoRepository
import com.flatcode.simplecomposeapps.utils.DATA
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val repository: TodoRepository, state: SavedStateHandle
) : ViewModel() {

    val taskId = state.get<Int>("taskId") ?: -1
    val isEditMode = taskId != -1
    var task by mutableStateOf<Task?>(null)
        private set

    val taskName = state.getLiveData("taskName", "")
    val taskImportant = state.getLiveData("taskImportant", false)

    init {
        if (taskId != -1) {
            viewModelScope.launch {
                task = repository.getTaskById(taskId)
                task?.let {
                    taskName.value = it.name
                    taskImportant.value = it.important
                }
            }
        }
    }

    private val _addEditTaskEvent = MutableSharedFlow<AddEditTaskEvent>()
    val addEditTaskEvent: SharedFlow<AddEditTaskEvent> = _addEditTaskEvent

    fun onSaveClick() {
        val name = taskName.value ?: ""
        val important = taskImportant.value ?: false
        if (name.isBlank()) {
            return
        }

        val currentTask = task
        if (currentTask != null) {
            val updatedTask = currentTask.copy(name = name, important = important)
            updateTask(updatedTask)
        } else {
            val newTask = Task(name = name, important = important)
            createTask(newTask)
        }
    }

    private fun createTask(task: Task) = viewModelScope.launch {
        repository.insertTask(task)
        _addEditTaskEvent.emit(AddEditTaskEvent.NavigateBackWithResult(DATA.ADD_RESULT_OK))
    }

    private fun updateTask(task: Task) = viewModelScope.launch {
        repository.updateTask(task)
        _addEditTaskEvent.emit(AddEditTaskEvent.NavigateBackWithResult(DATA.EDIT_RESULT_OK))
    }

    sealed class AddEditTaskEvent {
        data class NavigateBackWithResult(val result: Int) : AddEditTaskEvent()
    }
}