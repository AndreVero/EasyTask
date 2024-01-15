package statistic.domain.model

import androidx.compose.ui.graphics.Path

data class Statistic(
    val percentOfCompletion: Int,
    val icon: Path,
    val title: String
)