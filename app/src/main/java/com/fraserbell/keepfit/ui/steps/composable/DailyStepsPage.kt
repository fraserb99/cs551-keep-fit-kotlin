package com.fraserbell.keepfit.ui.steps.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.fraserbell.keepfit.ui.steps.DailyStepsViewModel

@Composable
fun DailyStepsPage(index: Int, vm: DailyStepsViewModel = hiltViewModel()) {
    val day by vm.getStepsForDayIndex(index).collectAsState(initial = null)

    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        StepBar(steps = day?.steps ?: 0, goal = day?.stepGoal ?: 10000)
    }
}