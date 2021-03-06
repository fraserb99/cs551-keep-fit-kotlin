package com.fraserbell.keepfit.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fraserbell.keepfit.data.dao.DailyStepsDao
import com.fraserbell.keepfit.data.dao.GoalDao
import com.fraserbell.keepfit.data.entities.DailySteps
import com.fraserbell.keepfit.data.entities.Goal

@Database(entities = [Goal::class, DailySteps::class], version = 10)
@TypeConverters(DataTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dayDao(): DailyStepsDao
    abstract fun goalDao(): GoalDao
}