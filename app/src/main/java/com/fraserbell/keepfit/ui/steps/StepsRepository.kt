package com.fraserbell.keepfit.ui.steps

import com.fraserbell.keepfit.data.entities.Day
import kotlinx.coroutines.flow.Flow

interface StepsRepository {
    fun getById(dayId: Int): Flow<Day>
    suspend fun addStepsToDay(dayId: Int, steps: Int)
}