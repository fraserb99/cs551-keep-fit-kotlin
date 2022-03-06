package com.fraserbell.keepfit.ui.goals

import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fraserbell.keepfit.data.DataStoreManager
import com.fraserbell.keepfit.data.entities.Goal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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

    val scaffoldState = mutableStateOf(ScaffoldState(
        DrawerState(DrawerValue.Closed) { false },
        SnackbarHostState()
    ))

    val goals = goalsRepository.getAllGoals()
    val activeGoal = goalsRepository.getActiveGoalId()
    val allowEditing = dataStoreManager.goalsEditable

    fun deleteGoalAsync(goal:Goal) = viewModelScope.launch {
        goalsRepository.delete(goal)
        goalToDelete.value = null
        currentOpenItem.value = null
        val res = scaffoldState.value.snackbarHostState.showSnackbar(
            "Goal Deleted",
            "Undo"
        )

        if (res == SnackbarResult.ActionPerformed) {
            goalsRepository.add(goal)
        }
    }

    fun addGoalAsync(goal: Goal) = viewModelScope.async {
        goalsRepository.add(goal)
    }

    fun updateGoalAsync(goal: Goal) = viewModelScope.async {
        goalsRepository.update(goal)
    }

    fun checkGoalNameExists(name: String, currentId: Int = -1) = viewModelScope.async {
        return@async goalsRepository.getGoalNameCount(name, currentId) > 0
    }

    fun onEditClicked(goal: Goal) = viewModelScope.launch {
        activeGoal.collect { activeId ->
            if (activeId == goal.goalId) {
                currentOpenItem.value = null
                scaffoldState.value.snackbarHostState.showSnackbar("Cannot edit the currently active goal")
            } else {
                goalToEdit.value = goal
            }
        }
    }

    fun onDeleteClicked(goal: Goal) = viewModelScope.launch {
        activeGoal.collect { activeId ->
            if (activeId == goal.goalId) {
                currentOpenItem.value = null
                scaffoldState.value.snackbarHostState.showSnackbar("Cannot delete the currently active goal")
            } else {
                goalToDelete.value = goal
            }
        }
    }
}