package com.fraserbell.keepfit.ui.goals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fraserbell.keepfit.data.entities.Goal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalsViewModel @Inject constructor(private val goalsRepository: GoalsRepository) : ViewModel() {
    val goals = goalsRepository.getAllGoals()

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