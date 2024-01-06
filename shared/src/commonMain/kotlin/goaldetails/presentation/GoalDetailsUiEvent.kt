package goaldetails.presentation

sealed interface GoalDetailsUiEvent {
    data class ShowError(val message: String): GoalDetailsUiEvent
}