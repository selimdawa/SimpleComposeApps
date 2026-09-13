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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val repository: TodoRepository, state: SavedStateHandle
) : ViewModel() {

    val taskId = state.get<Int>("taskId") ?: -1
    val isEditMode = taskId != -1
    var task by mutableStateOf<Task?>(null)
        private set

    private val _taskName = MutableStateFlow(state.get<String>("taskName") ?: "")
    val taskName: StateFlow<String> = _taskName.asStateFlow()

    private val _taskImportant = MutableStateFlow(state.get<Boolean>("taskImportant") ?: false)
    val taskImportant: StateFlow<Boolean> = _taskImportant.asStateFlow()

    init {
        if (taskId != -1) {
            viewModelScope.launch {
                task = repository.getTaskById(taskId)
                task?.let {
                    _taskName.value = it.name
                    _taskImportant.value = it.important
                }
            }
        }
    }

    fun updateTaskName(name: String) {
        _taskName.value = name
    }

    fun updateTaskImportance(important: Boolean) {
        _taskImportant.value = important
    }

    private val _addEditTaskEvent = MutableSharedFlow<AddEditTaskEvent>()
    val addEditTaskEvent: SharedFlow<AddEditTaskEvent> = _addEditTaskEvent

    fun onSaveClick() {
        val name = _taskName.value
        val important = _taskImportant.value
        if (name.isBlank()) {
            return
        }

        val currentTask = task
        if (currentTask != null) {
            Timber.d("Updating existing task: %d", currentTask.id)
            val updatedTask = currentTask.copy(name = name, important = important)
            updateTask(updatedTask)
        } else {
            Timber.d("Creating new task")
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