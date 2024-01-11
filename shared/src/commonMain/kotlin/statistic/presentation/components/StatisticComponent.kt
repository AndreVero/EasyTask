package statistic.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp

@Composable
fun StatisticComponent(
    points: List<Int>,
    xAxisValues: List<Int> = listOf(1, 2, 3, 4, 5, 6, 7),
    yAxisValues: List<Int> = listOf(10, 20, 30, 40, 50, 60, 70, 80, 90, 100),
    verticalStep: Int = 10,
    modifier: Modifier = Modifier
) {

    val textMeasurer = rememberTextMeasurer()
    val pathPortion = remember { Animatable(0f) }

    LaunchedEffect(true) {
        pathPortion.animateTo(
            targetValue = 1f,
            animationSpec = tween(2000)
        )
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val xAxisStep = size.width / xAxisValues.size
            val yAxisStep = size.height / yAxisValues.size
            val coordinates = mutableListOf<Offset>()
            val controlPoint1 = mutableListOf<Offset>()
            val controlPoint2 = mutableListOf<Offset>()

            xAxisValues.forEachIndexed { i, item ->
                val text = textMeasurer.measure(item.toString())
                drawText(
                    textLayoutResult = text,
                    topLeft = Offset(
                        xAxisStep * (i + 1),
                        size.height - 16.dp.toPx(),
                    ),
                    color = Color.White
                )
            }
            yAxisValues.forEachIndexed { i, item ->
                val text = textMeasurer.measure(item.toString())
                val y = size.height - ((i + 1) * yAxisStep) - (text.size.height / 2f)

                drawText(
                    textLayoutResult = text,
                    topLeft = Offset(
                        0f,
                        y,
                    ),
                    color = Color.White
                )
            }
            points.forEachIndexed { i, point ->
                val x1 = xAxisValues[i] * xAxisStep
                val y1 = size.height - (yAxisStep * (point / verticalStep.toFloat()))
                coordinates.add(Offset(x = x1, y = y1))
            }

            for (i in 1..<coordinates.size) {
                val x = (coordinates[i - 1].x + coordinates[i].x) / 2f
                controlPoint1.add(Offset(x, coordinates[i - 1].y))
                controlPoint2.add(Offset(x, coordinates[i].y))
            }

            val stroke = Path().apply {
                reset()
                moveTo(coordinates.first().x, coordinates.first().y)
                for (i in 0..<coordinates.size - 1) {
                    cubicTo(
                        controlPoint1[i].x, controlPoint1[i].y,
                        controlPoint2[i].x, controlPoint2[i].y,
                        coordinates[i + 1].x, coordinates[i + 1].y
                    )
                }
            }

            val outPath1 = Path()

            drawCircle(
                color = Color.White,
                radius = 10f,
                coordinates.first()
            )

            PathMeasure().apply {
                setPath(stroke, false)
                getSegment(0f, pathPortion.value * length, outPath1)
            }

            drawPath(
                outPath1,
                color = Color.White,
                style = Stroke(width = 1.dp.toPx())
            )

            drawCircle(
                color = Color.White,
                radius = 10f * pathPortion.value,
                coordinates.last()
            )
        }
    }
}