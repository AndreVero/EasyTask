package tasks.presentation

sealed interface TasksUiEvent {
    data class ShowError(val message: String): TasksUiEvent
}