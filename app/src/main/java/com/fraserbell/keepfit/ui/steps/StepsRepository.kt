package com.fraserbell.keepfit.ui.steps

import com.fraserbell.keepfit.data.entities.DailySteps
import com.fraserbell.keepfit.data.entities.DayWithGoal
import com.fraserbell.keepfit.data.entities.Goal
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface StepsRepository {
    fun getById(dayId: LocalDate): Flow<DayWithGoal>
    suspend fun addStepsToDay(dayId: LocalDate, steps: Int)
    suspend fun updateDailyGoal(dayId: LocalDate, goalId: Int)
}