package com.fraserbell.keepfit.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.NO_ACTION
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    foreignKeys = [ForeignKey(
        childColumns = ["dailyGoalId"],
        parentColumns = ["goalId"],
        entity = Goal::class,
        onDelete = NO_ACTION
    )]
)
data class DailySteps(
    @PrimaryKey val dayId: LocalDate,
    val steps: Int = 0,
    val dailyGoalId: Int? = null
)
