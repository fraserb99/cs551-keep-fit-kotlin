package com.fraserbell.keepfit.data.entities

import androidx.room.*
import java.time.LocalDate

@Entity
data class DailySteps(
    @PrimaryKey val dayId: LocalDate,
    val steps: Int = 0,
    @Embedded
    val goal: GoalDetails? = null
)

data class GoalDetails(
    val goalId: Int,
    val stepGoal: Int,
    val name: String
)
