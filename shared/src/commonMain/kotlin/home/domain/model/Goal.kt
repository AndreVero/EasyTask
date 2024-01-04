package home.domain.model

data class Goal(
    val title: String,
    val description: String,
    val goalPath: GoalPath
)