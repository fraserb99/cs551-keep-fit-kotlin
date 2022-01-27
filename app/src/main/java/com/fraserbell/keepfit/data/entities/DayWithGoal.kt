package com.fraserbell.keepfit.data.entities

import androidx.room.Embedded

data class DayWithGoal(
    @Embedded val dailySteps: DailySteps,
    val goal: Goal
)
