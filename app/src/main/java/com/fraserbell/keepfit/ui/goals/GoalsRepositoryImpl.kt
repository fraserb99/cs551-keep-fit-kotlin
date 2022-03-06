package com.fraserbell.keepfit.ui.goals

import com.fraserbell.keepfit.data.dao.GoalDao
import com.fraserbell.keepfit.data.entities.Goal
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GoalsRepositoryImpl @Inject constructor(private val goalDao: GoalDao): GoalsRepository {
    override fun getAllGoals(): Flow<List<Goal>> {
        return goalDao.getAll()
    }

    override fun getActiveGoalId(): Flow<Int?> {
        return goalDao.getActiveGoalId()
    }

    override suspend fun delete(goal: Goal) {
        goalDao.delete(goal)
    }

    override suspend fun add(goal: Goal) {
        goalDao.addGoal(goal)
    }

    override suspend fun update(goal: Goal) {
        goalDao.updateGoal(goal)
    }

    override suspend fun getGoalNameCount(name: String, currentId: Int?): Int {
        return goalDao.getNameCount(name, currentId)
    }
}