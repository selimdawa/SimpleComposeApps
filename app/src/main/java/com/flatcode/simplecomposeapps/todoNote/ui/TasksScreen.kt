package com.flatcode.simplecomposeapps.todoNote.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.flatcode.simplecomposeapps.todoNote.data.Task
import com.flatcode.simplecomposeapps.todoNote.viewmodel.TasksViewModel
import com.flatcode.simplecomposeapps.ui.theme.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.MC_TRACK
import com.flatcode.simplecomposeapps.ui.theme.Strings

@Composable
fun TasksScreen(
    navController: NavHostController,
    onBack: () -> Unit,
    onAddTask: () -> Unit,
    onEditTask: (Task) -> Unit,
    viewModel: TasksViewModel = hiltViewModel()
) {
    val tasks by viewModel.tasks.observeAsState(emptyList())
    val searchQuery by viewModel.searchQuery.observeAsState("")
    val preferences by viewModel.preferencesFlow.collectAsState(initial = null)
    val resultState =
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Int>("add_edit_result")
            ?.observeAsState()
    val result = resultState?.value

    LaunchedEffect(result) {
        result?.let {
            viewModel.onAddEditResult(it)
            navController.currentBackStackEntry?.savedStateHandle?.remove<Int>("add_edit_result")
        }
    }

    val snackbarHostState = remember { SnackbarHostState() }
    var showDeleteAllDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.tasksEvent.collect { event ->
            when (event) {
                is TasksViewModel.TasksEvent.NavigateToAddTaskScreen -> onAddTask()
                is TasksViewModel.TasksEvent.NavigateToEditTaskScreen -> onEditTask(event.task)
                is TasksViewModel.TasksEvent.ShowUndoDeleteTaskMessage -> {
                    val result = snackbarHostState.showSnackbar(
                        message = Strings.MSG_TASK_DELETED, actionLabel = Strings.UNDO
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onUndoDeleteClick(event.task)
                    }
                }

                is TasksViewModel.TasksEvent.ShowTaskSavedConfirmationMessage -> {
                    snackbarHostState.showSnackbar(event.msg)
                }

                is TasksViewModel.TasksEvent.NavigateToDeleteAllCompletedTasksScreen -> {
                    showDeleteAllDialog = true
                }
            }
        }
    }

    if (showDeleteAllDialog) {
        TodoDeleteDialog(
            message = Strings.DIALOG_DELETE_MESSAGE_TASKS,
            onConfirm = {
                viewModel.onConfirmDeleteAllCompletedClick()
                showDeleteAllDialog = false
            },
            onDismiss = { showDeleteAllDialog = false }
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
        TodoTopAppBar(
            title = Strings.TASKS,
            onBack = onBack,
            onSearchQueryChange = { viewModel.searchQuery.value = it },
            searchQuery = searchQuery,
            onSortOrderSelected = { viewModel.onSortOrderSelected(it) },
            showHideCompleted = true,
            hideCompleted = preferences?.hideCompleted ?: false,
            onHideCompletedClick = { viewModel.onHideCompletedClick(it) },
            onDeleteAllClick = { viewModel.onDeleteAllCompletedClick() },
            deleteAllText = Strings.DELETE_COMPLETED_TASKS,
            hasBack = false
        )
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = { viewModel.onAddNewTaskClick() },
            containerColor = MC_TRACK,
            contentColor = Color.White,
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 0.dp),
            modifier = Modifier.padding(end = 25.dp)
        ) {
            Icon(imageVector = AppIcons.Add, contentDescription = Strings.ADD_TASK)
        }
    }, snackbarHost = { SnackbarHost(snackbarHostState) }, containerColor = COLOR_ON_BACKGROUND
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (tasks.isEmpty()) {
                Text(
                    text = Strings.NO_TASKS_FOUND,
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(tasks, key = { it.id }) { task ->
                        val dismissState = rememberSwipeToDismissBoxState()

                        LaunchedEffect(dismissState.currentValue) {
                            if (dismissState.currentValue != SwipeToDismissBoxValue.Settled) {
                                viewModel.onTaskSwiped(task)
                            }
                        }

                        LaunchedEffect(task) {
                            if (dismissState.currentValue != SwipeToDismissBoxValue.Settled) {
                                dismissState.snapTo(SwipeToDismissBoxValue.Settled)
                            }
                        }

                        SwipeToDismissBox(state = dismissState, backgroundContent = {
                            val color = when (dismissState.targetValue) {
                                SwipeToDismissBoxValue.StartToEnd -> Color.Transparent
                                SwipeToDismissBoxValue.EndToStart -> Color.Transparent
                                else -> Color.Transparent
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color)
                                    .padding(horizontal = 20.dp),
                                contentAlignment = if (dismissState.targetValue == SwipeToDismissBoxValue.StartToEnd) Alignment.CenterStart else Alignment.CenterEnd
                            ) {
                                if (dismissState.targetValue != SwipeToDismissBoxValue.Settled) {
                                    Icon(
                                        imageVector = AppIcons.Delete,
                                        contentDescription = "Delete",
                                        tint = Color.White
                                    )
                                }
                            }
                        }, content = {
                            TaskItem(
                                task = task,
                                onCheckedChange = { viewModel.onTaskCheckedChanged(task, it) },
                                modifier = Modifier.clickable { viewModel.onTaskSelected(task) })
                        })
                    }
                }
            }
        }
    }
}