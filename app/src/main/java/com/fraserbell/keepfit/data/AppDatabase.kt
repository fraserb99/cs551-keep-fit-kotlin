package com.fraserbell.keepfit.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fraserbell.keepfit.data.dao.DayDao
import com.fraserbell.keepfit.data.dao.GoalDao
import com.fraserbell.keepfit.data.entities.Day
import com.fraserbell.keepfit.data.entities.Goal

@Database(entities = [Goal::class, Day::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dayDao(): DayDao
    abstract fun goalDao(): GoalDao
}