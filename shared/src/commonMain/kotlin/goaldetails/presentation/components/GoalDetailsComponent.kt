package goaldetails.presentation.components

import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import goaldetails.domain.model.GoalDetails

@Composable
fun GoalDetailsComponent(
    task: String,
    goalDetails: GoalDetails,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
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
            text = goalDetails.title,
            color = Color.White,
            style = MaterialTheme.typography.body2,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = goalDetails.description,
            color = Color.White,
            style = MaterialTheme.typography.body2,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.width(160.dp),
            onClick = {},
        ) {
            Text(
                text = "Done",
                color = Color.Black,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h4,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}