package com.fraserbell.keepfit.ui.steps.composable

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.Duration
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

fun Date.addDays(days: Int): Date {
    return Date.from(this.toInstant().minus(Duration.ofDays(days.toLong())))
}

@Composable
fun DaysOfWeekTabPage(startDate: LocalDate, currentDate: LocalDate) {

    Column {
        Row {
            for (i in 0..6) {
                val date = startDate.plusDays(i.toLong())
                Surface(
                    modifier = Modifier.weight(1f),
                    elevation = if (startDate == currentDate) 8.dp else 0.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = date.dayOfMonth.toString())
                            Text(
                                text = date.dayOfWeek.getDisplayName(
                                    TextStyle.SHORT_STANDALONE,
                                    Locale.UK
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}