package com.fraserbell.keepfit.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fraserbell.keepfit.data.entities.Goal
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {
    @Insert
    suspend fun addGoal(goal: Goal)

    @Query("SELECT * FROM goal")
    fun getAll(): Flow<List<Goal>>

    @Update
    suspend fun updateGoal(goal: Goal)

    @Delete
    suspend fun delete(goal: Goal)
}