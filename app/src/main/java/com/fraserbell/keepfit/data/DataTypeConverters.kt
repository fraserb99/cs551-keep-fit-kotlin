package com.fraserbell.keepfit.data

import androidx.room.TypeConverter
import java.time.LocalDate

class DataTypeConverters {
    @TypeConverter
    fun fromEpochDay(value: Long): LocalDate {
        return LocalDate.ofEpochDay(value)
    }

    @TypeConverter
    fun localDateToEpochDay(date: LocalDate): Long {
        return date.toEpochDay()
    }
}