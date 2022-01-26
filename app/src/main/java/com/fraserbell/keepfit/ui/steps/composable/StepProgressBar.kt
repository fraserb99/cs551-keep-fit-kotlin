package com.fraserbell.keepfit.ui.steps.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StepBar(steps: Int, goal: Int) {
    val progress = steps.toFloat() / goal

    Row(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                Modifier
                    .height(400.dp)
                    .width(85.dp)
                    .background(Color(red = 0, green = 0, blue = 0, alpha = 0)),
                shape = RoundedCornerShape(12.dp),
                elevation = 3.dp
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .rotate(180f)
                        .background(Color.Black.copy(0.1f))
                ) {
                    val canvasHeight = size.height
                    val canvasWidth = size.width
                    drawRect(
                        size = Size(height = canvasHeight * progress,
                            width = canvasWidth),
                        color = getProgressColour(progress)
                    )
                }
            }
            Text("%,d".format(steps), fontSize = 42.sp, fontWeight = FontWeight.ExtraBold)
            Text("Steps", style = MaterialTheme.typography.caption, color = MaterialTheme.colors.onBackground)
        }
    }
}

fun getProgressColour(progress: Float): Color {
    return when {
        progress >= 1 -> {
            Color(0xFF8FD66E)
        }
        progress > 0.75 -> {
            Color(0xFFE0E25E)
        }
        progress > 0.25 -> {
            Color(0xFFE2A55E)
        }
        else -> {
            Color(0xFFE26D5E)
        }
    }
}