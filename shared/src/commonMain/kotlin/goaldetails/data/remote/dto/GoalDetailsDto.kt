package goaldetails.data.remote.dto

import androidx.compose.ui.graphics.vector.PathParser
import goaldetails.domain.model.GoalDetails
import kotlinx.serialization.Serializable

@Serializable
data class GoalDetailsDto(
    val title: String,
    val description: String,
    val icon: String,
    val motivation: String
)

fun GoalDetailsDto.toGoalDetails() : GoalDetails {
    return GoalDetails(
        title = this.title,
        description = this.description,
        icon = PathParser().parsePathString(this.icon)
            .toPath(),
        motivation = this.motivation
    )
}