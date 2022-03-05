package com.fraserbell.keepfit.ui.history

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fraserbell.keepfit.data.entities.DailySteps
import com.fraserbell.keepfit.ui.steps.StepsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val stepsRepository: StepsRepository) : ViewModel() {
    var currentDate = mutableStateOf<LocalDate?>(null)
        private set
    var showClearModal = mutableStateOf(false)
        private set

    fun getStepsForDate(date: LocalDate): Flow<DailySteps> {
        return stepsRepository.getById(date)
    }

    fun setCurrentDate(date: LocalDate?) {
        currentDate.value = date
    }

    fun confirmClearHistory() {
        showClearModal.value = true
    }
    fun cancelClearHistory() {
        showClearModal.value = false
    }

    fun clearHistory() = viewModelScope.async {
        stepsRepository.clearHistory()
    }
}