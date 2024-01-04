package tasks.data.remote.dto

import androidx.compose.ui.graphics.vector.PathParser
import kotlinx.serialization.Serializable
import tasks.domain.model.Task

@Serializable
data class TaskDto(
    val title: String,
    val description: String,
    val icon: String,
    val motivation: String
)

fun TaskDto.toTask() : Task {
    return Task(
        title = this.title,
        description = this.description,
        icon = PathParser().parsePathString(this.icon)
            .toPath(),
        motivation = this.motivation
    )
}