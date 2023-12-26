package main.presentation

import main.domain.model.Task

data class MainState(
    val isLoading: Boolean = false,
    val tasks: List<Task> = emptyList()
)
