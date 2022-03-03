package com.fraserbell.keepfit.ui.goals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fraserbell.keepfit.data.DataStoreManager
import com.fraserbell.keepfit.data.entities.Goal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalsViewModel @Inject constructor(
    private val goalsRepository: GoalsRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
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

    fun goalNameExists(name: String, maxCount: Int = 0) = viewModelScope.async {
        val count = goalsRepository.getGoalNameCount(name)
        return@async count > maxCount
    }
}