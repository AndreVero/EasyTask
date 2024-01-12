package statistic.presentation

import statistic.domain.model.Statistic

data class StatisticUiState(
    val isLoading: Boolean = false,
    val statistics: List<Statistic> = emptyList()
)
