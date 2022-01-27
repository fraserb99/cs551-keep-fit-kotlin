package com.fraserbell.keepfit.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.fraserbell.keepfit.data.entities.DailySteps
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface DayDao {
    @Insert
    suspend fun insert(dailySteps: DailySteps)

    @Query("SELECT * FROM dailysteps")
    fun getAll(): Flow<List<DailySteps>>

    @Query("SELECT * FROM DailySteps WHERE dayId = :id")
    fun getById(id: LocalDate): Flow<DailySteps>

    @Update
    suspend fun update(dailySteps: DailySteps)

    @Query("UPDATE DailySteps SET steps = :steps WHERE dayId = :dayId")
    suspend fun update(dayId: LocalDate, steps: Int)

    @Query("UPDATE DailySteps SET steps = steps + :steps WHERE dayId = :dayId")
    suspend fun addSteps(dayId: LocalDate, steps: Int)

    @Query("UPDATE DailySteps SET dailyGoalId = :goalId WHERE dayId = :dayId")
    suspend fun updateGoal(dayId: LocalDate, goalId: Int)

    @Query("SELECT EXISTS(SELECT * FROM DailySteps WHERE dayId = :dayId)")
    suspend fun exists(dayId: LocalDate): Boolean
}