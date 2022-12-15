package com.commandiron.wheel_picker_compose.core

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


internal sealed class SnappedDateTime(val snappedLocalDateTime: CompatDateTime, val snappedIndex: Int) {
    data class DayOfMonth (val localDateTime: CompatDateTime, val index: Int): SnappedDateTime(localDateTime, index)
    data class Month (val localDateTime: CompatDateTime, val index: Int): SnappedDateTime(localDateTime, index)
    data class Year (val localDateTime: CompatDateTime, val index: Int): SnappedDateTime(localDateTime, index)
    data class Hour (val localDateTime: CompatDateTime, val index: Int): SnappedDateTime(localDateTime, index)
    data class Minute (val localDateTime: CompatDateTime, val index: Int): SnappedDateTime(localDateTime, index)
}

internal sealed class SnappedDate(val snappedLocalDate: CompatDate, val snappedIndex: Int) {
    data class DayOfMonth (val localDate: CompatDate, val index: Int): SnappedDate(localDate, index)
    data class Month(val localDate: CompatDate, val index: Int): SnappedDate(localDate, index)
    data class Year (val localDate: CompatDate, val index: Int): SnappedDate(localDate, index)
}

internal sealed class SnappedTime(val snappedLocalTime: CompatTime, val snappedIndex: Int) {
    data class Hour (val localTime: CompatTime, val index: Int): SnappedTime(localTime, index)
    data class Minute (val localTime: CompatTime, val index: Int): SnappedTime(localTime, index)
}