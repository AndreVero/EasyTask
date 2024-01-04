package home.presentation

import home.domain.model.Goal

data class HomeState(
    val isLoading: Boolean = false,
    val goals: List<Goal> = emptyList()
)
