package com.fraserbell.keepfit.ui.steps

import androidx.lifecycle.ViewModel
import com.fraserbell.keepfit.data.entities.DailySteps
import com.fraserbell.keepfit.data.entities.DayWithGoal
import com.fraserbell.keepfit.data.entities.Goal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DailyStepsViewModel @Inject constructor(private val stepsRepository: StepsRepository) : ViewModel() {
    fun getStepsForDate(date: LocalDate): Flow<DayWithGoal> {
        return stepsRepository.getById(date)
    }

    fun getStepsForDayIndex(dayIndex: Int): Flow<DayWithGoal> {
        val date = LocalDate.now().minusDays(dayIndex.toLong())

        return stepsRepository.getById(date)
    }

    suspend fun updateDailyGoal(dayId: LocalDate, goalId: Int) {
        stepsRepository.updateDailyGoal(dayId, goalId)
    }
}