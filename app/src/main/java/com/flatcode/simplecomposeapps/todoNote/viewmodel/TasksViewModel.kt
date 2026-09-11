package com.flatcode.simplecomposeapps.todoNote.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.todoNote.data.PreferencesManager
import com.flatcode.simplecomposeapps.todoNote.data.SortOrder
import com.flatcode.simplecomposeapps.todoNote.data.Task
import com.flatcode.simplecomposeapps.todoNote.data.TaskDao
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.DATA
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class TasksViewModel @Inject constructor(
    private val taskDao: TaskDao,
    private val preferencesManager: PreferencesManager,
    state: SavedStateHandle
) : ViewModel() {

    val searchQuery = state.getLiveData("searchQuery", "")
    val preferencesFlow = preferencesManager.preferencesFlow

    private val taskEventChannel = Channel<TasksEvent>()
    val tasksEvent = taskEventChannel.receiveAsFlow()

    private val tasksFlow = combine(
        searchQuery.asFlow(),
        preferencesFlow
    ) { query, filterPreferences ->
        Pair(query, filterPreferences)
    }.flatMapLatest { (query, filterPreferences) ->
        taskDao.getTasks(query, filterPreferences.sortOrder, filterPreferences.hideCompleted)
    }

    val tasks = tasksFlow.asLiveData()

    fun onSortOrderSelected(sortOrder: SortOrder) = viewModelScope.launch {
        preferencesManager.updateSortOrder(sortOrder)
    }

    fun onHideCompletedClick(hideCompleted: Boolean) = viewModelScope.launch {
        preferencesManager.updateHideCompleted(hideCompleted)
    }

    fun onTaskSelected(task: Task) = viewModelScope.launch {
        taskEventChannel.send(TasksEvent.NavigateToEditTaskScreen(task))
    }

    fun onTaskCheckedChanged(task: Task, isChecked: Boolean) = viewModelScope.launch {
        taskDao.update(task.copy(completed = isChecked))
    }

    fun onTaskSwiped(task: Task) = viewModelScope.launch {
        taskDao.delete(task)
        taskEventChannel.send(TasksEvent.ShowUndoDeleteTaskMessage(listOf(task)))
    }

    fun onUndoDeleteClick(tasks: List<Task>) = viewModelScope.launch {
        taskDao.insertAll(tasks)
    }

    fun onAddNewTaskClick() = viewModelScope.launch {
        taskEventChannel.send(TasksEvent.NavigateToAddTaskScreen)
    }

    fun onDeleteAllCompletedClick() = viewModelScope.launch {
        taskEventChannel.send(TasksEvent.NavigateToDeleteAllCompletedTasksScreen)
    }

    fun onConfirmDeleteAllCompletedClick() = viewModelScope.launch {
        val completedTasks = taskDao.getCompletedTasksList()
        taskDao.deleteCompletedTasks()
        if (completedTasks.isNotEmpty()) {
            taskEventChannel.send(TasksEvent.ShowUndoDeleteTaskMessage(completedTasks))
        }
    }

    fun onAddEditResult(result: Int) = viewModelScope.launch {
        when (result) {
            DATA.ADD_RESULT_OK -> taskEventChannel.send(TasksEvent.ShowTaskSavedConfirmationMessage(Strings.MSG_TASK_ADDED))
            DATA.EDIT_RESULT_OK -> taskEventChannel.send(TasksEvent.ShowTaskSavedConfirmationMessage(Strings.MSG_TASK_UPDATED))
        }
    }

    sealed class TasksEvent {
        data object NavigateToAddTaskScreen : TasksEvent()
        data class NavigateToEditTaskScreen(val task: Task) : TasksEvent()
        data class ShowUndoDeleteTaskMessage(val tasks: List<Task>) : TasksEvent()
        data class ShowTaskSavedConfirmationMessage(val msg: String) : TasksEvent()
        data object NavigateToDeleteAllCompletedTasksScreen : TasksEvent()
    }
}