package com.fraserbell.keepfit.data.dao

import androidx.room.*
import com.fraserbell.keepfit.data.entities.Goal
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {
    @Insert
    fun addGoal(goal: Goal)

    @Query("SELECT * FROM goal")
    fun getAll(): Flow<List<Goal>>

    @Update
    fun updateGoal(goal: Goal)

    @Delete
    fun delete(goal: Goal)
}