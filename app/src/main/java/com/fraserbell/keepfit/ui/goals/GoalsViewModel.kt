package com.fraserbell.keepfit.ui.goals

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GoalsViewModel @Inject constructor(private val goalsRepository: GoalsRepository) : ViewModel() {
    val goals = goalsRepository.getAllGoals()
}