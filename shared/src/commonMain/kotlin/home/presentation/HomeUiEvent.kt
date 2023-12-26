package home.presentation

sealed interface HomeUiEvent {
    data class ShowError(val message: String): HomeUiEvent

}