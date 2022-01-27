package com.fraserbell.keepfit.ui.steps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fraserbell.keepfit.data.dao.DayDao
import com.fraserbell.keepfit.data.dao.GoalDao
import com.fraserbell.keepfit.data.entities.Day
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class StepsViewModel @Inject constructor(private val stepsRepository: StepsRepository) : ViewModel() {
    fun getDayById(dayId: LocalDate): Flow<Day> {
        return stepsRepository.getById(dayId)
    }

    fun addStepsToDayAsync(dayId: LocalDate, steps: Int) = viewModelScope.async {
        stepsRepository.addStepsToDay(dayId, steps)
    }
}