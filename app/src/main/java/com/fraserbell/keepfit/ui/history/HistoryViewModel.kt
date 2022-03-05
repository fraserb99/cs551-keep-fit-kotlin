package com.fraserbell.keepfit.ui.history

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fraserbell.keepfit.data.entities.DayWithGoal
import com.fraserbell.keepfit.ui.steps.StepsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val stepsRepository: StepsRepository) : ViewModel() {
    var currentDate = mutableStateOf<LocalDate?>(null)
    val currentSteps = currentDate.value?.let { getStepsForDate(it) }

    fun getStepsForDate(date: LocalDate): Flow<DayWithGoal> {
        return stepsRepository.getById(date)
    }

    fun clearHistory() = viewModelScope.async {
        stepsRepository.clearHistory()
    }
}