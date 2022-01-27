package com.fraserbell.keepfit.ui.steps.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.Placeholder
import com.fraserbell.keepfit.ui.theme.Black10
import com.fraserbell.keepfit.ui.theme.Transparent
import com.fraserbell.keepfit.ui.theme.White10
import com.fraserbell.keepfit.util.daysFrom
import com.fraserbell.keepfit.util.weeksFrom
import java.time.Duration
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

fun Date.addDays(days: Int): Date {
    return Date.from(this.toInstant().minus(Duration.ofDays(days.toLong())))
}

fun getItemColor(selected: Boolean, isLightTheme: Boolean): Color {
    return if (isLightTheme) {
        if (selected) Black10 else Transparent
    } else {
        if (selected) White10 else Transparent
    }
}

@Composable
fun DaysOfWeekTabPage(startDate: LocalDate, currentDate: LocalDate, onDateChange: (date: LocalDate) -> Unit) {
    val weekDiff = currentDate.weeksFrom(startDate)
    val selectedIndex = when {
        weekDiff == 0 -> currentDate.daysFrom(startDate)
        weekDiff > 0 -> 6
        else -> 0
    }

    Column {
        TabRow(selectedTabIndex = selectedIndex) {
            for (i in 0..6) {
                val date = startDate.plusDays(i.toLong())
                Tab(
                    text = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = date.dayOfMonth.toString())
                            Text(
                                text = date.dayOfWeek.getDisplayName(
                                    TextStyle.SHORT_STANDALONE,
                                    Locale.UK
                                ).substring(0..0)
                            )
                        }
                    },
                    selected = currentDate == date,
                    onClick = { onDateChange(date) },
                    enabled = !date.isAfter(LocalDate.now())
                )
            }
        }
    }
}

//Row {
//    for (i in 0..6) {
//        val date = startDate.plusDays(i.toLong())
//        Surface(
//            modifier = Modifier
//                .weight(1f)
//                .clickable(enabled = !date.isAfter(LocalDate.now())) {
//                    onDateChange(date)
//                },
//            color = getItemColor(date == currentDate, MaterialTheme.colors.isLight),
//        ) {
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Column(
//                    modifier = Modifier.padding(0.dp, 4.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Text(text = date.dayOfMonth.toString())
//                    Text(
//                        text = date.dayOfWeek.getDisplayName(
//                            TextStyle.SHORT_STANDALONE,
//                            Locale.UK
//                        )
//                    )
//                }
//                Divider(
//                    modifier = Modifier.alpha(if (date == currentDate) 1f else 0f),
//                    thickness = 4.dp
//                )
//            }
//        }
//    }
//}