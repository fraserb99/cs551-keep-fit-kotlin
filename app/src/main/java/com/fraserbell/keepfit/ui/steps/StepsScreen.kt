package com.fraserbell.keepfit.ui.steps

import android.graphics.drawable.shapes.RoundRectShape
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun StepsScreen(navController: NavController) {
    Column(Modifier.fillMaxHeight().fillMaxWidth()) {
        Header(date = "20/01/2020")
        StepBar(steps = 2345, goal = 10000)
    }
}

@Composable
fun Header(date: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(Dp(24f))) {
        Text(text = date, fontSize = 30.sp)
    }
}

@Composable
fun StepBar(steps: Int, goal: Int) {
    Row(Modifier.fillMaxWidth().fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            Modifier.fillMaxHeight().fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Canvas(modifier = Modifier
                .height(400.dp)
                .width(85.dp)
                .rotate(180f)
                ) {
                val canvasHeight = size.height
                val canvasWidth = size.width
                drawRoundRect(size = Size(height = canvasHeight, width = canvasWidth), cornerRadius = CornerRadius(12.dp.toPx()), color = Color(red = 0, green = 0, blue = 0, alpha = 10))
                drawRoundRect(size = Size(height = canvasHeight * steps / goal, width = canvasWidth), cornerRadius = CornerRadius(12.dp.toPx()), color = Color.Red)
            }
            Text("%,d".format(steps), fontSize = 30.sp)
        }
    }
}