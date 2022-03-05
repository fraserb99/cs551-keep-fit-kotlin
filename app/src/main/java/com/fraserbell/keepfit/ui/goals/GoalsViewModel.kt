package com.fraserbell.keepfit.ui.goals

import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fraserbell.keepfit.data.DataStoreManager
import com.fraserbell.keepfit.data.entities.Goal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class GoalsViewModel @Inject constructor(
    private val goalsRepository: GoalsRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    var addDialogOpen = mutableStateOf(false)
    var goalToEdit = mutableStateOf<Goal?>(null)
    var goalToDelete = mutableStateOf<Goal?>(null)
    var currentOpenItem = mutableStateOf<Int?>(null)

    val scaffoldState = mutableStateOf(ScaffoldState(DrawerState(DrawerValue.Closed) { false }, SnackbarHostState()))

    val goals = goalsRepository.getAllGoals()
    val allowEditing = dataStoreManager.goalsEditable

    fun deleteGoalAsync(goal:Goal) = viewModelScope.async {
        goalsRepository.delete(goal)
    }

    fun addGoalAsync(goal: Goal) = viewModelScope.async {
        goalsRepository.add(goal)
    }

    fun updateGoalAsync(goal: Goal) = viewModelScope.async {
        goalsRepository.update(goal)
    }
}