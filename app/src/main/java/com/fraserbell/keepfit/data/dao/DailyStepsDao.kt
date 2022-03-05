package com.fraserbell.keepfit.data.dao

import androidx.room.*
import com.fraserbell.keepfit.data.entities.DailySteps
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface DailyStepsDao {
    @Insert
    suspend fun insert(dailySteps: DailySteps)

    @Transaction
    @Query("SELECT * FROM DailySteps WHERE dayId = :id")
    fun getById(id: LocalDate): Flow<DailySteps>

    @Update
    suspend fun update(dailySteps: DailySteps)

    @Query("UPDATE DailySteps SET steps = :steps WHERE dayId = :dayId")
    suspend fun update(dayId: LocalDate, steps: Int)

    @Query("UPDATE DailySteps SET steps = steps + :steps WHERE dayId = :dayId")
    suspend fun addSteps(dayId: LocalDate, steps: Int)

    @Query(
        "UPDATE DailySteps " +
                "SET goalId = :goalId, " +
                "name = (SELECT Goal.name FROM Goal WHERE Goal.goalId = :goalId), " +
                "stepGoal = (SELECT Goal.stepGoal FROM Goal WHERE Goal.goalId = :goalId) " +
                "WHERE dayId = :dayId"
    )
    suspend fun updateGoal(dayId: LocalDate, goalId: Int)

    @Query("SELECT EXISTS(SELECT * FROM DailySteps WHERE dayId = :dayId)")
    suspend fun exists(dayId: LocalDate): Boolean

    @Query("DELETE from DailySteps")
    suspend fun clearHistory()
}