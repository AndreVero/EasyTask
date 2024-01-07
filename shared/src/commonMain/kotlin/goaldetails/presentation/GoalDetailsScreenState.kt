package goaldetails.presentation

import goaldetails.domain.model.GoalDetails

data class GoalDetailsScreenState(
    val isLoading: Boolean = false,
    val goalDetails: List<GoalDetails> = emptyList()
)
