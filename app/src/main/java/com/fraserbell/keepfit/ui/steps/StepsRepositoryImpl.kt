package com.fraserbell.keepfit.ui.steps

import com.fraserbell.keepfit.data.dao.DayDao
import com.fraserbell.keepfit.data.entities.DailySteps
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class StepsRepositoryImpl @Inject constructor(private val dayDao: DayDao) : StepsRepository {
    override fun getById(dayId: LocalDate): Flow<DailySteps> {
        return dayDao.getById(dayId)
    }


    override suspend fun addStepsToDay(dayId: LocalDate, steps: Int) {
        if (!dayDao.exists(dayId)) {
            dayDao.insert(DailySteps(dayId, steps, 10000))
        } else {
            dayDao.addSteps(dayId, steps)
        }
    }
}