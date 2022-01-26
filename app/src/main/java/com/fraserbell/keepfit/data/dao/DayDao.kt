package com.fraserbell.keepfit.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.fraserbell.keepfit.data.entities.Day
import com.fraserbell.keepfit.data.entities.DayWithGoal
import kotlinx.coroutines.flow.Flow

@Dao
interface DayDao {
    @Insert
    suspend fun insert(day: Day)

    @Query("SELECT * FROM day")
    fun getAll(): Flow<List<Day>>

    @Query("SELECT * FROM Day WHERE dayId = :id")
    fun getById(id: Int): Flow<Day>

    @Update
    suspend fun update(day: Day)

    @Query("UPDATE Day SET steps = :steps WHERE dayId = :dayId")
    suspend fun update(dayId: Int, steps: Int)

    @Query("UPDATE Day SET steps = steps + :steps WHERE dayId = :dayId")
    suspend fun addSteps(dayId: Int, steps: Int)

    @Query("UPDATE Day SET stepGoal = :stepGoal WHERE dayId = :dayId")
    suspend fun updateGoal(dayId: Int, stepGoal: Int)

    @Query("SELECT EXISTS(SELECT * FROM Day WHERE dayId = :dayId)")
    suspend fun exists(dayId: Int): Boolean
}