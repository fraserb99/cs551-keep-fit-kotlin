package com.fraserbell.keepfit.ui.goals

import com.fraserbell.keepfit.data.dao.GoalDao
import com.fraserbell.keepfit.data.entities.Goal
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GoalsRepositoryImpl @Inject constructor(private val goalDao: GoalDao): GoalsRepository {
    override fun getAllGoals(): Flow<List<Goal>> {
        return goalDao.getAll()
    }
}