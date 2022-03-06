package com.fraserbell.keepfit.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fraserbell.keepfit.data.entities.Goal
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface GoalDao {
    @Insert
    suspend fun addGoal(goal: Goal)

    @Query("SELECT * FROM Goal")
    fun getAll(): Flow<List<Goal>>

    @Query("SELECT goalId FROM DailySteps WHERE dayId = :date")
    fun getActiveGoalId(date: LocalDate = LocalDate.now()): Flow<Int?>

    @Update
    suspend fun updateGoal(goal: Goal)

    @Delete
    suspend fun delete(goal: Goal)

    @Query("SELECT COUNT(*) FROM Goal WHERE name=:name and goalId != :currentId")
    suspend fun getNameCount(name: String, currentId: Int?): Int
}