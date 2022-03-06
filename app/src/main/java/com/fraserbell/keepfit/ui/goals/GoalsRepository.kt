package com.fraserbell.keepfit.ui.goals

import com.fraserbell.keepfit.data.entities.Goal
import kotlinx.coroutines.flow.Flow

interface GoalsRepository {
    fun getAllGoals(): Flow<List<Goal>>
    fun getActiveGoalId(): Flow<Int?>
    suspend fun delete(goal: Goal)
    suspend fun add(goal: Goal)
    suspend fun update(goal: Goal)
    suspend fun getGoalNameCount(name: String, currentId: Int?): Int
}