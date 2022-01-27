package com.fraserbell.keepfit.ui.steps.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

@Composable
fun TabMonthHeader(currentDayId: LocalDate) {
    val background = if (MaterialTheme.colors.isLight) MaterialTheme.colors.primary else MaterialTheme.colors.surface
    val textColour = if (MaterialTheme.colors.isLight) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface

    Row(
        Modifier
            .fillMaxWidth()
            .background(background),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            currentDayId.month.getDisplayName(TextStyle.FULL, Locale.UK),
            style = MaterialTheme.typography.overline,
            color = textColour
        )
    }
}