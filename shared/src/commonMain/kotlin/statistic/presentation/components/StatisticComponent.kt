package statistic.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.dp
import statistic.domain.model.Statistic

@Composable
fun StatisticComponent(statistic: Statistic) {

    Column(
        modifier = Modifier.border(1.dp, Color.White, RoundedCornerShape(20.dp)).padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = statistic.title,
                color = Color.White,
                style = MaterialTheme.typography.body2
            )
            Spacer(modifier = Modifier.width(16.dp))
            Canvas(modifier = Modifier.size(24.dp)) {
                val center = this.center
                val bounds = statistic.icon.getBounds()
                translate(left = center.x + bounds.width, center.y + bounds.height) {
                    scale(3f) {
                        drawPath(
                            path = statistic.icon,
                            color = Color.White
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        GraphComponent(
            points = statistic.points,
            modifier = Modifier.height(300.dp).width(250.dp))
    }
}