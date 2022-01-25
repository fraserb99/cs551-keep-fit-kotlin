package com.fraserbell.keepfit.ui.steps

import android.graphics.drawable.shapes.RoundRectShape
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.Room
import com.fraserbell.keepfit.data.AppDatabase
import com.fraserbell.keepfit.data.entities.Goal

@Composable
fun StepsScreen(navController: NavController) {
    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()) {
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
                        .background(Color(red = 0, green = 0, blue = 0, alpha = 15))
                ) {
                    val canvasHeight = size.height
                    val canvasWidth = size.width
                    drawRoundRect(
                        size = Size(height = canvasHeight * steps / goal,
                        width = canvasWidth),
                        cornerRadius = CornerRadius(12.dp.toPx()),
                        color = Color(0xFFE26D5E)
                    )
                }
            }
            Text("%,d".format(steps), fontSize = 36.sp, fontWeight = FontWeight.Bold)
        }
    }
}