package tasks.presentation

import tasks.domain.model.Task

data class TasksScreenState(
    val isLoading: Boolean = false,
    val tasks: List<Task> = emptyList()
)
