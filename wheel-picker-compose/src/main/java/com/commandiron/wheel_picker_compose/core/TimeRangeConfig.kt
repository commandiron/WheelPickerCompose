package com.commandiron.wheel_picker_compose.core

data class TimeRangeConfig(
    val startHour: Int = 0,
    val endHour: Int = 23,
    val startHourAmPm: Int = 1,
    val endHourAmPm: Int = 12,
    val startMinute: Int = 0,
    val endMinute: Int = 59,
)
