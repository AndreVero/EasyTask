package tasks.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.dp
import tasks.domain.model.Task

@Composable
fun TaskComponent(
    task: Task,
    modifier: Modifier = Modifier
) {

    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Color.White, RoundedCornerShape(
                topStartPercent = 20,
                topEndPercent = 80,
                bottomStartPercent = 20,
                bottomEndPercent = 20)
            )
            .padding(16.dp)

    ) {
        Column(modifier = Modifier.align(Alignment.TopStart)){
            Text(
                text = "Title: " + task.title,
                color = Color.White,
                style = MaterialTheme.typography.body2,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Description: " + task.description,
                color = Color.White,
                style = MaterialTheme.typography.body2,
            )
            Spacer(modifier = Modifier.height(8.dp))
            AnimatedContent(expanded) {isExpanded ->
                if (isExpanded) {
                    Text(
                        text = "Motivation: " + task.motivation,
                        color = Color.White,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.width(330.dp).clickable {
                            expanded = false
                        }
                    )
                } else {
                    Text(
                        text = "Tap to see motivation...",
                        color = Color.White,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.clickable {
                            expanded = true
                        }
                    )
                }
            }

        }
        Canvas(modifier = Modifier.size(24.dp).align(Alignment.BottomEnd)) {
            val center = this.center
            val bounds = task.icon.getBounds()
            translate(left = center.x + bounds.width, center.y + bounds.height) {
                scale(3f) {
                    drawPath(
                        path = task.icon,
                        color = Color.White
                    )
                }
            }
        }

    }
}