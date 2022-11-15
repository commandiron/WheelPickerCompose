package com.commandiron.wheel_picker_compose.core

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


internal sealed class SnappedDateTime(val snappedLocalDateTime: LocalDateTime, val snappedIndex: Int) {
    data class DayOfMonth (val localDateTime: LocalDateTime, val index: Int): SnappedDateTime(localDateTime, index)
    data class Month (val localDateTime: LocalDateTime, val index: Int): SnappedDateTime(localDateTime, index)
    data class Year (val localDateTime: LocalDateTime, val index: Int): SnappedDateTime(localDateTime, index)
    data class Hour (val localDateTime: LocalDateTime, val index: Int): SnappedDateTime(localDateTime, index)
    data class Minute (val localDateTime: LocalDateTime, val index: Int): SnappedDateTime(localDateTime, index)
}

internal sealed class SnappedDate(val snappedLocalDate: LocalDate, val snappedIndex: Int) {
    data class DayOfMonth (val localDate: LocalDate, val index: Int): SnappedDate(localDate, index)
    data class Month(val localDate: LocalDate, val index: Int): SnappedDate(localDate, index)
    data class Year (val localDate: LocalDate, val index: Int): SnappedDate(localDate, index)
}

internal sealed class SnappedTime(val snappedLocalTime: LocalTime, val snappedIndex: Int) {
    data class Hour (val localTime: LocalTime, val index: Int): SnappedTime(localTime, index)
    data class Minute (val localTime: LocalTime, val index: Int): SnappedTime(localTime, index)
}