package com.fraserbell.keepfit.ui.goals

import com.fraserbell.keepfit.data.entities.Goal
import kotlinx.coroutines.flow.Flow

interface GoalsRepository {
    fun getAllGoals(): Flow<List<Goal>>
    suspend fun delete(goal: Goal)
    suspend fun add(goal: Goal)
    suspend fun update(goal: Goal)
}