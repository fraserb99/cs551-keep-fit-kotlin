package com.fraserbell.keepfit.ui.steps

import android.widget.Toast
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fraserbell.keepfit.data.DataStoreManager
import com.fraserbell.keepfit.data.entities.DailySteps
import com.fraserbell.keepfit.navigation.Screen
import com.fraserbell.keepfit.ui.goals.GoalsRepository
import com.fraserbell.keepfit.util.getIsoDayOfWeek
import com.fraserbell.keepfit.util.getStartOfWeek
import com.fraserbell.keepfit.util.isInSameWeek
import com.fraserbell.keepfit.util.withDayOfWeek
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class StepsViewModel @Inject constructor(
    private val stepsRepository: StepsRepository,
    private val dataManager: DataStoreManager,
    private val goalsRepository: GoalsRepository
) : ViewModel() {
    val allowHistoricalRecording = dataManager.historyRecording
    private val currentAllowHistoryRec = mutableStateOf(false)
    val goals = goalsRepository.getAllGoals()

    val scaffoldState = mutableStateOf(ScaffoldState(DrawerState(DrawerValue.Closed) { false }, SnackbarHostState()))
    @ExperimentalPagerApi
    val pagerState = mutableStateOf(PagerState(Int.MAX_VALUE, 0))
    @ExperimentalPagerApi
    val weekPagerState = mutableStateOf(PagerState(Int.MAX_VALUE, 0))

    var currentDayId = mutableStateOf(LocalDate.now())

    var goalDialogVisible = mutableStateOf(false)
    var addDialogVisible = mutableStateOf(false)

    private var snackBarShowing = false

    init {
        collectHistoricalRecording()
    }

    fun setInitialDate(epochDay: Long?) {
        if (!isToday()) return

        currentDayId.value = epochDay?.let {
            if (epochDay == 0L) LocalDate.now() else LocalDate.ofEpochDay(epochDay)
        } ?: LocalDate.now()

        return
    }

    fun isToday(): Boolean {
        return currentDayId.value == LocalDate.now()
    }

    fun getDayDiff(): Int {
        return (LocalDate.now().toEpochDay() - currentDayId.value.toEpochDay()).toInt()
    }

    fun getWeekDiff(): Int {
        return ((
                LocalDate.now().getStartOfWeek().plusDays(6).toEpochDay() -
                        currentDayId.value.toEpochDay()
                ) / 7).toInt()
    }

    fun onDayChange(index: Int) {
        val date = LocalDate.now().minusDays(index.toLong())
        currentDayId.value = date
    }

    fun onWeekChange(index: Int) {
        val date = LocalDate.now().minusWeeks(index.toLong()).withDayOfWeek(currentDayId.value.getIsoDayOfWeek())

        if (!date.isInSameWeek(currentDayId.value)) {
            currentDayId.value = if (!date.isAfter(LocalDate.now())) date else LocalDate.now()
        }
    }

    @ExperimentalPagerApi
    suspend fun syncPagers() {
        pagerState.value.animateScrollToPage(
            getDayDiff()
        )
        weekPagerState.value.animateScrollToPage(
            getWeekDiff()
        )
    }

    fun getDayById(dayId: LocalDate): Flow<DailySteps> {
        return stepsRepository.getById(dayId)
    }

    fun addStepsToDayAsync(dayId: LocalDate, steps: Int) = viewModelScope.async {
        stepsRepository.addStepsToDay(dayId, steps)
    }

    fun getStepsForDate(date: LocalDate): Flow<DailySteps> {
        return stepsRepository.getById(date)
    }

    fun getStepsForDayIndex(dayIndex: Int): Flow<DailySteps> {
        val date = LocalDate.now().minusDays(dayIndex.toLong())

        return stepsRepository.getById(date)
    }

    fun updateDailyGoalAsync(dayId: LocalDate, goalId: Int) = viewModelScope.launch {
        try {
            stepsRepository.updateDailyGoal(dayId, goalId)
        } catch (e: Exception) {

        } finally {
            goalDialogVisible.value = false
        }
    }

    fun showAddDialog(onNavigate: (route: String) -> Unit) = viewModelScope.launch {
        if (currentAllowHistoryRec.value || isToday()) {
            addDialogVisible.value = true
        } else if (!snackBarShowing) {
            snackBarShowing = true
            val result = scaffoldState.value.snackbarHostState.showSnackbar(
                "Historical recording disabled",
                "Settings"
            )
            snackBarShowing = false
            if (result == SnackbarResult.ActionPerformed) {
                onNavigate(Screen.Settings.route)
            }
        }
    }

    private fun collectHistoricalRecording() = viewModelScope.launch {
        allowHistoricalRecording.collect {
            currentAllowHistoryRec.value = it
        }
    }
}