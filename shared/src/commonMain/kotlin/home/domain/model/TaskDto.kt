package home.domain.model

import androidx.compose.ui.graphics.vector.PathParser
import kotlinx.serialization.Serializable

@Serializable
data class TaskDto(
    val title: String,
    val description: String,
    val icon: String,
)

fun TaskDto.toTaskUiModel() : Task {
    val path = PathParser().parsePathString(this.icon)
        .toPath()

    return Task(
        title = this.title,
        description = this.description,
        taskPath = TaskPath(
            path = path,
            bounds = path.getBounds()
        )
    )
}
