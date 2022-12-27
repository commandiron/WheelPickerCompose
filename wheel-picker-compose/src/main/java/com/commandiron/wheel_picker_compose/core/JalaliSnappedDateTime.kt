package com.commandiron.wheel_picker_compose.core

import saman.zamani.persiandate.PersianDate
import java.time.LocalDate

internal sealed class JalaliSnappedDate(val snappedLocalDate: PersianDate, val snappedIndex: Int) {
    data class DayOfMonth(val localDate: PersianDate, val index: Int) : JalaliSnappedDate(localDate, index)
    data class Month(val localDate: PersianDate, val index: Int) : JalaliSnappedDate(localDate, index)
    data class Year(val localDate: PersianDate, val index: Int) : JalaliSnappedDate(localDate, index)
}
