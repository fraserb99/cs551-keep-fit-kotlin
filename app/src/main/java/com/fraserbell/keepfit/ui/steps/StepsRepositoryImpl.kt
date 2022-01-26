package com.fraserbell.keepfit.ui.steps

import com.fraserbell.keepfit.data.dao.DayDao
import com.fraserbell.keepfit.data.entities.Day
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StepsRepositoryImpl @Inject constructor(private val dayDao: DayDao) : StepsRepository {
    override fun getById(dayId: Int): Flow<Day> {
        return dayDao.getById(dayId)
    }


    override suspend fun addStepsToDay(dayId: Int, steps: Int) {
        if (!dayDao.exists(dayId)) {
            dayDao.insert(Day(dayId, steps, 10000))
        } else {
            dayDao.addSteps(dayId, steps)
        }
    }

}