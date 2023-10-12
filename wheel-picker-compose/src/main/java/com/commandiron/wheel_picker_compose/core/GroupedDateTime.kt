package com.commandiron.wheel_picker_compose.core

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


internal sealed class GroupedDateTime(val groupedLocalDateTime: LocalDateTime) {
    data class Date(val localDateTime: LocalDateTime): GroupedDateTime(localDateTime)
    data class Hour(val localDateTime: LocalDateTime): GroupedDateTime(localDateTime)
    data class Minute(val localDateTime: LocalDateTime): GroupedDateTime(localDateTime)
}

internal sealed class GroupedDate(val groupedLocalDate: LocalDate) {
    data class Date(val localDate: LocalDate): GroupedDate(localDate)
}