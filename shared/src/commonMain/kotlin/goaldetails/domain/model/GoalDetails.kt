package goaldetails.domain.model

import androidx.compose.ui.graphics.Path

data class GoalDetails(
    val title: String,
    val description: String,
    val icon: Path,
    val tasks: List<TaskInfo>

)
