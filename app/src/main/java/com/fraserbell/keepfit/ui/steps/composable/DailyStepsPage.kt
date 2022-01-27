package com.fraserbell.keepfit.ui.steps.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.fraserbell.keepfit.ui.steps.DailyStepsViewModel

@Composable
fun DailyStepsPage(index: Int, vm: DailyStepsViewModel = hiltViewModel()) {
    val dayWithSteps by vm.getStepsForDayIndex(index).collectAsState(initial = null)
    val dailySteps = dayWithSteps?.dailySteps
    val goal = dayWithSteps?.goal

    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        StepBar(steps = dailySteps?.steps ?: 0, goal = goal?.stepGoal ?: 10000)
    }
}