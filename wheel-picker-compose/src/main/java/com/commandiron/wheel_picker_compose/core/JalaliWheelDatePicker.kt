package com.commandiron.wheel_picker_compose.core

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import java.text.DateFormatSymbols
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
internal fun JalaliWheelDatePicker(
    modifier: Modifier = Modifier,
    startDate: LocalDate = LocalDate.now(),
    yearsRange: IntRange? = IntRange(1300, 1500),
    backwardsDisabled: Boolean = false,
    size: DpSize = DpSize(256.dp, 128.dp),
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textColor: Color = LocalContentColor.current,
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
    onSnappedDate: (snappedDate: SnappedDate) -> Int? = { _ -> null }
) {
    var snappedDate by remember { mutableStateOf(startDate) }
    var dayOfMonths = calculateDayOfMonths(snappedDate.month.value, snappedDate.year)
    val monthNames =
        arrayOf("فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور", "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند")

    val months = (1..12).map {
        Month(
            text = monthNames[it - 1], value = it, index = it - 1
        )
    }

    val years = yearsRange?.map {
        Year(
            text = it.toString(), value = it, index = yearsRange.indexOf(it)
        )
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        if (selectorProperties.enabled().value) {
            Surface(
                modifier = Modifier.size(size.width, size.height / 3),
                shape = selectorProperties.shape().value,
                color = selectorProperties.color().value,
                border = selectorProperties.border().value
            ) {}
        }
        Row {
            //Day of Month
            WheelTextPicker(size = DpSize(
                width = if (yearsRange == null) size.width / 2 else size.width / 3, height = size.height
            ),
                texts = dayOfMonths.map { it.text },
                style = textStyle,
                color = textColor,
                selectorProperties = WheelPickerDefaults.selectorProperties(
                    enabled = false
                ),
                startIndex = dayOfMonths.find { it.value == startDate.dayOfMonth }?.index ?: 0,
                onScrollFinished = { snappedIndex ->

                    val newDayOfMonth = dayOfMonths.find { it.index == snappedIndex }?.value

                    newDayOfMonth?.let {
                        val newDate = snappedDate.withDayOfMonth(newDayOfMonth)

                        val isDateBefore = isDateBefore(newDate, startDate)

                        if (backwardsDisabled) {
                            if (!isDateBefore) {
                                snappedDate = newDate
                            }
                        } else {
                            snappedDate = newDate
                        }

                        val newIndex = dayOfMonths.find { it.value == snappedDate.dayOfMonth }?.index

                        newIndex?.let {
                            onSnappedDate(
                                SnappedDate.DayOfMonth(
                                    localDate = snappedDate, index = newIndex
                                )
                            )?.let { return@WheelTextPicker it }
                        }
                    }

                    return@WheelTextPicker dayOfMonths.find { it.value == snappedDate.dayOfMonth }?.index
                })
            //Month
            WheelTextPicker(size = DpSize(
                width = if (yearsRange == null) size.width / 2 else size.width / 3, height = size.height
            ),
                texts = months.map { it.text },
                style = textStyle,
                color = textColor,
                selectorProperties = WheelPickerDefaults.selectorProperties(
                    enabled = false
                ),
                startIndex = months.find { it.value == startDate.monthValue }?.index ?: 0,
                onScrollFinished = { snappedIndex ->

                    val newMonth = months.find { it.index == snappedIndex }?.value

                    newMonth?.let {

                        val newDate = snappedDate.withMonth(newMonth)

                        val isDateBefore = isDateBefore(newDate, startDate)

                        if (backwardsDisabled) {
                            if (!isDateBefore) {
                                snappedDate = newDate
                            }
                        } else {
                            snappedDate = newDate
                        }

                        dayOfMonths = calculateDayOfMonths(snappedDate.month.value, snappedDate.year)

                        val newIndex = months.find { it.value == snappedDate.monthValue }?.index

                        newIndex?.let {
                            onSnappedDate(
                                SnappedDate.Month(
                                    localDate = snappedDate, index = newIndex
                                )
                            )?.let { return@WheelTextPicker it }
                        }
                    }


                    return@WheelTextPicker months.find { it.value == snappedDate.monthValue }?.index
                })
            //Year
            years?.let { years ->
                WheelTextPicker(size = DpSize(
                    width = size.width / 3, height = size.height
                ),
                    texts = years.map { it.text },
                    style = textStyle,
                    color = textColor,
                    selectorProperties = WheelPickerDefaults.selectorProperties(
                        enabled = false
                    ),
                    startIndex = years.find { it.value == startDate.year }?.index ?: 0,
                    onScrollFinished = { snappedIndex ->

                        val newYear = years.find { it.index == snappedIndex }?.value

                        newYear?.let {

                            val newDate = snappedDate.withYear(newYear)

                            val isDateBefore = isDateBefore(newDate, startDate)

                            if (backwardsDisabled) {
                                if (!isDateBefore) {
                                    snappedDate = newDate
                                }
                            } else {
                                snappedDate = newDate
                            }

                            dayOfMonths = calculateDayOfMonths(snappedDate.month.value, snappedDate.year)

                            val newIndex = years.find { it.value == snappedDate.year }?.index

                            newIndex?.let {
                                onSnappedDate(
                                    SnappedDate.Year(
                                        localDate = snappedDate, index = newIndex
                                    )
                                )?.let { return@WheelTextPicker it }

                            }
                        }

                        return@WheelTextPicker years.find { it.value == snappedDate.year }?.index
                    })
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun isDateBefore(date: LocalDate, currentDate: LocalDate): Boolean {
    return date.isBefore(currentDate)
}


@RequiresApi(Build.VERSION_CODES.O)
private fun calculateDayOfMonths(month: Int, year: Int): List<DayOfMonth> {
    val month31day = (1..31).map {
        DayOfMonth(
            text = it.toString(), value = it, index = it - 1
        )
    }
    val month30day = (1..30).map {
        DayOfMonth(
            text = it.toString(), value = it, index = it - 1
        )
    }
    val month29day = (1..29).map {
        DayOfMonth(
            text = it.toString(), value = it, index = it - 1
        )
    }
    return when (month) {
        1, 2, 3, 4, 5, 6 -> month31day
        7, 8, 9, 10, 11 -> month30day
        12 -> if (isLeapYear(year)) month30day else month29day
        else -> month30day
    }
}

private fun isLeapYear(year: Int): Boolean {
    val matches = intArrayOf(1, 5, 9, 13, 17, 22, 26, 30)
    val reminder = year % 33
    for (match in matches) {
        if (reminder == match) return true
    }
    return false
}