package com.fraserbell.keepfit.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Day(
    @PrimaryKey val dayId: Int,
    val steps: Int = 0,
    val stepGoal: Int?
)
