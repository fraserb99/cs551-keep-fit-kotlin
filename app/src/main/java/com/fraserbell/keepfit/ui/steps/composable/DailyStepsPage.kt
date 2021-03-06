package com.fraserbell.keepfit.ui.steps.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.fraserbell.keepfit.data.entities.DailySteps
import kotlinx.coroutines.flow.Flow

@Composable
fun DailyStepsPage(stepsFlow: Flow<DailySteps?>) {
    val dailySteps by stepsFlow.collectAsState(initial = null)
    val goal = dailySteps?.goal

    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        StepBar(steps = dailySteps?.steps ?: 0, goal = goal?.stepGoal)
    }
}