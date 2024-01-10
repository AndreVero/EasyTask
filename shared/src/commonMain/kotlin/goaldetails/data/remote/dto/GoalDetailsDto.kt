package goaldetails.data.remote.dto

import androidx.compose.ui.graphics.vector.PathParser
import goaldetails.domain.model.GoalDetails
import goaldetails.domain.model.TaskInfo
import kotlinx.serialization.Serializable
import kotlin.random.Random
import kotlin.random.nextInt

@Serializable
data class GoalDetailsDto(
    val title: String,
    val description: String,
    val icon: String,
    val tasks: List<Map<String, String>>
)

fun GoalDetailsDto.toGoalDetails() : GoalDetails {
    return GoalDetails(
        title = this.title + Random.nextInt(0..100),
        description = this.description,
        icon = PathParser().parsePathString(this.icon)
            .toPath(),
        tasks = this.tasks.map { TaskInfo(
            title = it["title"] ?: "",
            percent = it["percent"] ?: ""
        ) }
    )
}