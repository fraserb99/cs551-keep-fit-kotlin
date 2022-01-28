package com.fraserbell.keepfit.ui.steps

import com.fraserbell.keepfit.data.dao.DailyStepsDao
import com.fraserbell.keepfit.data.entities.DailySteps
import com.fraserbell.keepfit.data.entities.DayWithGoal
import com.fraserbell.keepfit.data.entities.Goal
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class StepsRepositoryImpl @Inject constructor(private val dailyStepsDao: DailyStepsDao) : StepsRepository {
    override fun getById(dayId: LocalDate): Flow<DayWithGoal> {
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
            dailyStepsDao.insert(DailySteps(dayId, 0, goalId))
        } else {
            dailyStepsDao.updateGoal(dayId, goalId)
        }
    }
}