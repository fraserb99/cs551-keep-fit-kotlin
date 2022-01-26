package com.fraserbell.keepfit.util

import java.text.SimpleDateFormat
import java.util.*

fun Date.toInt(): Int {
    val sdf = SimpleDateFormat("yyyyMMdd")
    val str = sdf.format(this)

    return str.toInt()
}