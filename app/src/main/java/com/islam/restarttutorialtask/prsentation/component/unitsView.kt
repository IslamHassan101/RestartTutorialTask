package com.islam.restarttutorialtask.prsentation.component


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.islam.restarttutorialtask.prsentation.ui.theme.dGreen
import com.islam.restarttutorialtask.prsentation.ui.theme.gGreen
import com.islam.restarttutorialtask.prsentation.ui.theme.lGreen
import kotlin.math.max

@OptIn(ExperimentalTextApi::class)
@Composable
fun UnitView(
    number: String,
    innerCircleColor: Color = dGreen,
    middleCircleColor: Color = gGreen,
    outerCircleColor: Color = lGreen,
    textColor: Color = lGreen,
    textStyle: TextStyle = TextStyle(fontSize = 24.sp),
    circleSize: Dp = 100.dp,
    innerCirclePadding: Dp = 10.dp,
    middleCirclePadding: Dp = 0.dp,
    outerCirclePadding: Dp = 8.dp,
    dividerColor: Color = lGreen.copy(alpha = 0.5f),
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Canvas(
            modifier = Modifier
                .size(circleSize)
        ) {
            val circleRadiusPx = with(density) { circleSize.toPx() }
            val innerPaddingPx = with(density) { innerCirclePadding.toPx() }
            val middlePaddingPx = with(density) { middleCirclePadding.toPx() }
            val outerPaddingPx = with(density) { outerCirclePadding.toPx() }

            val measuredText = textMeasurer.measure(text = number, style = textStyle)
            val textSize =
                Size(measuredText.size.width.toFloat(), measuredText.size.height.toFloat())
            val canvasCenterX = circleRadiusPx / 2
            val canvasCenterY = circleRadiusPx / 2
            val textCenter =
                Offset(canvasCenterX - textSize.width / 2, canvasCenterY - textSize.height / 2)


            val innerCircleRadius = max(textSize.width, textSize.height) / 2 + innerPaddingPx
            drawCircle(
                color = innerCircleColor,
                radius = innerCircleRadius,
                center = Offset(canvasCenterX, canvasCenterY)
            )


            val middleCircleRadius = innerCircleRadius + middlePaddingPx
            drawCircle(
                color = middleCircleColor,
                radius = middleCircleRadius,
                center = Offset(canvasCenterX, canvasCenterY),
                style = Stroke(width = 4f)
            )

            val outerCircleRadius = middleCircleRadius + outerPaddingPx
            drawCircle(
                color = outerCircleColor,
                radius = outerCircleRadius,
                center = Offset(canvasCenterX, canvasCenterY),
                style = Stroke(width = 10f)
            )


            drawText(
                textLayoutResult = measuredText,
                topLeft = textCenter,
                color = textColor,
            )


            val dividerX = canvasCenterX
            val dividerStartY = canvasCenterY + outerCircleRadius
            val dividerEndY = circleRadiusPx
            drawLine(
                color = dividerColor,
                start = Offset(dividerX, dividerStartY),
                end = Offset(dividerX, dividerEndY),
                strokeWidth = 20f
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NumberWithDoubleCirclePreview() {
    Column {
        UnitView(number = "5", circleSize = 100.dp)
    }

}