package com.fraserbell.keepfit.ui.steps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fraserbell.keepfit.data.DataStoreManager
import com.fraserbell.keepfit.data.entities.DailySteps
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class StepsViewModel @Inject constructor(private val stepsRepository: StepsRepository, private val dataManager: DataStoreManager) : ViewModel() {
    val allowHistoricalRecording = dataManager.historyRecording

    fun getDayById(dayId: LocalDate): Flow<DailySteps> {
        return stepsRepository.getById(dayId)
    }

    fun addStepsToDayAsync(dayId: LocalDate, steps: Int) = viewModelScope.async {
        stepsRepository.addStepsToDay(dayId, steps)
    }
}