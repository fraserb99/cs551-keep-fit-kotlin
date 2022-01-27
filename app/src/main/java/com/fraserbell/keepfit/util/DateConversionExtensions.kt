package com.fraserbell.keepfit.util

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.*

fun Date.toInt(): Int {
    val sdf = SimpleDateFormat("yyyyMMdd")
    val str = sdf.format(this)

    return str.toInt()
}

fun LocalDate.getIsoDayOfWeek(): Long {
    val fieldISO = WeekFields.of(Locale.FRANCE).dayOfWeek()
    return this.get(fieldISO).toLong()
}

fun LocalDate.withDayOfWeek(dayOfWeek: Long): LocalDate {
    val fieldISO = WeekFields.of(Locale.FRANCE).dayOfWeek()
    return this.with(fieldISO, dayOfWeek)
}

fun LocalDate.getStartOfWeek(): LocalDate {
    return this.withDayOfWeek(1)
}

fun LocalDate.isInSameWeek(otherDate: LocalDate): Boolean {
    return this.getStartOfWeek() == otherDate.getStartOfWeek()
}

fun LocalDate.daysFrom(otherDate: LocalDate): Int {
    return (this.toEpochDay() - otherDate.toEpochDay()).toInt()
}

fun LocalDate.weeksFrom(otherDate: LocalDate): Int {
    val endOfWeek = this.getStartOfWeek()

    return (endOfWeek.daysFrom(otherDate.getStartOfWeek()) / 7)
}