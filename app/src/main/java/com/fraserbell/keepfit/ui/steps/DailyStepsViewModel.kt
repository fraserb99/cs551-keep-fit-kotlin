package com.fraserbell.keepfit.ui.steps

import androidx.lifecycle.ViewModel
import com.fraserbell.keepfit.data.entities.Day
import com.fraserbell.keepfit.util.toInt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import java.time.Duration
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DailyStepsViewModel @Inject constructor(private val stepsRepository: StepsRepository) : ViewModel() {
    fun getStepsForDayIndex(dayIndex: Int): Flow<Day> {
        val date = LocalDate.now().minusDays(dayIndex.toLong())

        return stepsRepository.getById(date)
    }
}