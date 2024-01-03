package home.domain.model

data class Task(
    val title: String,
    val description: String,
    val taskPath: TaskPath
)