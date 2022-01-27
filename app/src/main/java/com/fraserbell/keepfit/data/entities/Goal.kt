package com.fraserbell.keepfit.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Goal(
    @PrimaryKey(autoGenerate = true) val goalId: Int = 0,
    val stepCount: Int,
    val name: String,
)
