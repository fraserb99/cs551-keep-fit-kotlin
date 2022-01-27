package com.fraserbell.keepfit.ui.steps

import com.fraserbell.keepfit.data.entities.Day
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface StepsRepository {
    fun getById(dayId: LocalDate): Flow<Day>
    suspend fun addStepsToDay(dayId: LocalDate, steps: Int)
}