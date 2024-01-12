package statistic.data.remote.dto

import androidx.compose.ui.graphics.vector.PathParser
import kotlinx.serialization.Serializable
import statistic.domain.model.Statistic

@Serializable
data class StatisticDto(
    val points: List<Int>,
    val icon: String,
    val title: String
)

fun StatisticDto.toStatistic() : Statistic {
    return Statistic(
        points = this.points,
        title = this.title,
        icon  = PathParser().parsePathString(this.icon)
            .toPath()
    )
}