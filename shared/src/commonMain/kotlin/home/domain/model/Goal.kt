package home.domain.model

data class Goal(
    val id: String,
    val title: String,
    val description: String,
    val goalPath: GoalPath,
    val progress: Int,
)