package com.fraserbell.keepfit.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Day(
    @PrimaryKey val dayId: LocalDate,
    val steps: Int = 0,
    val stepGoal: Int?
)
