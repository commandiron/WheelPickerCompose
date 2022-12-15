package com.commandiron.wheel_picker_compose.core

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Calendar

data class CompatTime(val hour: Int, val minute: Int) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun toLocalTime(): LocalTime =
        LocalTime.of(hour, minute)

    fun toCalendarTime(): Calendar =
        Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }
}

data class CompatDate(val dayOfMonth: Int, val month: Int, val year: Int) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun toLocalDate(): LocalDate =
        LocalDate.of(year, month, dayOfMonth)

    fun toCalendarDate(): Calendar =
        Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
            set(Calendar.MONTH, month)
            set(Calendar.YEAR, year)
        }
}

data class CompatDateTime(val compatDate: CompatDate, val compatTime: CompatTime) {

    val minute: Int get() = compatTime.minute
    val hour: Int get() = compatTime.hour
    val dayOfMonth: Int get() = compatDate.dayOfMonth
    val month: Int get() = compatDate.month
    val year: Int get() = compatDate.year

    @RequiresApi(Build.VERSION_CODES.O)
    fun toLocalDateTime(): LocalDateTime =
        LocalDateTime.of(
            compatDate.year,
            compatDate.month,
            compatDate.dayOfMonth,
            compatTime.hour,
            compatTime.minute
        )

    fun toCalendarDateTime(): Calendar =
        Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, compatDate.dayOfMonth)
            set(Calendar.MONTH, compatDate.month)
            set(Calendar.YEAR, compatDate.year)
            set(Calendar.HOUR, compatTime.hour)
            set(Calendar.MINUTE, compatTime.minute)
        }
}