package statistic.presentation

sealed interface StatisticUiEvent {
    data class ShowError(val message: String): StatisticUiEvent
}