package com.fraserbell.keepfit.data.dao

import androidx.room.*
import com.fraserbell.keepfit.data.entities.DailySteps
import com.fraserbell.keepfit.data.entities.DayWithGoal
import com.fraserbell.keepfit.data.entities.Goal
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface DailyStepsDao {
    @Insert
    suspend fun insert(dailySteps: DailySteps)

//    @Query("SELECT * FROM DailySteps as day join Goal as goal on goalId = dailyGoalId")
//    fun getAll(): Flow<List<Map<DailySteps?, Goal?>>>

    @Transaction
    @Query("SELECT * FROM DailySteps WHERE dayId = :id")
    fun getById(id: LocalDate): Flow<DayWithGoal>

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

    @Query("DELETE from DailySteps")
    suspend fun clearHistory()
}