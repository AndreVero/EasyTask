package goaldetails.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import goaldetails.domain.model.GoalDetails
import goaldetails.domain.model.TaskInfo

@Composable
fun GoalDetailsComponent(
    taskInfo: TaskInfo,
    goalDetails: GoalDetails,
    modifier: Modifier = Modifier
) {

    var visibility by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        visibility = true
    }

    AnimatedVisibility(
        visibility,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(Color.Black)
                .border(1.dp, Color.White, RoundedCornerShape(20.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Canvas(modifier = Modifier.size(24.dp)) {
                val center = this.center
                val bounds = goalDetails.icon.getBounds()
                translate(left = center.x + bounds.width, center.y + bounds.height) {
                    scale(3f) {
                        drawPath(
                            path = goalDetails.icon,
                            color = Color.White
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = taskInfo.title,
                color = Color.White,
                style = MaterialTheme.typography.body2,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = taskInfo.percent,
                color = Color.White,
                style = MaterialTheme.typography.body2,
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

}