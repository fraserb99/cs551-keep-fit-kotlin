package com.fraserbell.keepfit.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fraserbell.keepfit.data.entities.Day
import com.fraserbell.keepfit.data.entities.DayWithGoal
import kotlinx.coroutines.flow.Flow

@Dao
interface DayDao {
    @Insert
    fun insert(day: Day)

    @Query("SELECT * FROM day JOIN goal ON day.dayGoalId = goal.goalId")
    fun getAll(): Flow<List<DayWithGoal>>
}