package com.fraserbell.keepfit.data.entities

import androidx.room.Embedded

data class DayWithGoal(
    @Embedded val day: Day,
    @Embedded val goal: Goal
)
