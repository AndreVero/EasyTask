package tasks.domain.model

import androidx.compose.ui.graphics.Path

data class Task(
    val title: String,
    val description: String,
    val icon: Path,
    val motivation: String
)
