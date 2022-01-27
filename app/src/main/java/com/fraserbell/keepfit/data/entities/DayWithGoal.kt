package com.fraserbell.keepfit.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class DayWithGoal(
    @Embedded val dailySteps: DailySteps,
    @Relation(
        parentColumn = "dailyGoalId",
        entityColumn = "goalId"
    )
    val goal: Goal?
)
