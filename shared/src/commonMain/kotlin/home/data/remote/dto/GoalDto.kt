package home.data.remote.dto

import androidx.compose.ui.graphics.vector.PathParser
import home.domain.model.Goal
import home.domain.model.GoalPath
import kotlinx.serialization.Serializable

@Serializable
data class GoalDto(
    val title: String,
    val description: String,
    val icon: String,
)

fun GoalDto.toGoal() : Goal {
    val path = PathParser().parsePathString(this.icon)
        .toPath()

    return Goal(
        title = this.title,
        description = this.description,
        goalPath = GoalPath(
            path = path,
            bounds = path.getBounds()
        )
    )
}
