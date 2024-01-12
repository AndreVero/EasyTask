package statistic.domain.model

import androidx.compose.ui.graphics.Path

data class Statistic(
    val points: List<Int>,
    val icon: Path,
    val title: String
)