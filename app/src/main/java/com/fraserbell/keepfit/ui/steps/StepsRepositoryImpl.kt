package com.fraserbell.keepfit.ui.steps

import com.fraserbell.keepfit.data.dao.DailyStepsDao
import com.fraserbell.keepfit.data.entities.DailySteps
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class StepsRepositoryImpl @Inject constructor(private val dailyStepsDao: DailyStepsDao) : StepsRepository {
    override fun getById(dayId: LocalDate): Flow<DailySteps> {
        return dailyStepsDao.getById(dayId)
    }


    override suspend fun addStepsToDay(dayId: LocalDate, steps: Int) {
        if (!dailyStepsDao.exists(dayId)) {
            dailyStepsDao.insert(DailySteps(dayId, steps))
        } else {
            dailyStepsDao.addSteps(dayId, steps)
        }
    }

    override suspend fun updateDailyGoal(dayId: LocalDate, goalId: Int) {
        if (!dailyStepsDao.exists(dayId)) {
            dailyStepsDao.insert(DailySteps(dayId, 0))
            dailyStepsDao.updateGoal(dayId, goalId)
        } else {
            dailyStepsDao.updateGoal(dayId, goalId)
        }
    }

    override suspend fun clearHistory() {
        return dailyStepsDao.clearHistory()
    }
}