package com.fraserbell.keepfit.ui.goals

import com.fraserbell.keepfit.data.entities.Goal
import kotlinx.coroutines.flow.Flow

interface GoalsRepository {
    fun getAllGoals(): Flow<List<Goal>>
}