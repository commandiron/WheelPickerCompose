package com.commandiron.wheel_picker_compose.core

import android.os.Build
import android.util.Log
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
import saman.zamani.persiandate.PersianDate
import java.text.DateFormatSymbols
import java.time.LocalDate
import kotlin.math.log

@RequiresApi(Build.VERSION_CODES.O)
@Composable
internal fun JalaliWheelDatePicker(
    modifier: Modifier = Modifier,
    startDate: PersianDate = PersianDate(),
    yearsRange: IntRange? = IntRange(1300, 1500),
    backwardsDisabled: Boolean = false,
    size: DpSize = DpSize(256.dp, 128.dp),
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textColor: Color = LocalContentColor.current,
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
    onSnappedDate: (snappedDate: JalaliSnappedDate) -> Int? = { _ -> null }
) {
    var snappedDate by remember { mutableStateOf(startDate) }
    var dayOfMonths = calculateDayOfMonths(snappedDate.shMonth, snappedDate.shYear)
    val monthNames = snappedDate.monthList()

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
                startIndex = dayOfMonths.find { it.value == startDate.shDay }?.index ?: 0,
                onScrollFinished = { snappedIndex ->

                    val newDayOfMonth = dayOfMonths.find { it.index == snappedIndex }?.value

                    newDayOfMonth?.let {
                        val newDate = PersianDate(snappedDate.time)
                        newDate.shDay = it
                        val isDateBefore = newDate.before(startDate)

                        if (backwardsDisabled) {
                            if (!isDateBefore) {
                                snappedDate = newDate
                            }
                        } else {
                            snappedDate = newDate
                        }

                        val newIndex = dayOfMonths.find { it.value == snappedDate.shDay }?.index

                        newIndex?.let {
                            onSnappedDate(
                                JalaliSnappedDate.DayOfMonth(
                                    localDate = snappedDate, index = newIndex
                                )
                            )?.let { return@WheelTextPicker it }
                        }
                    }

                    return@WheelTextPicker dayOfMonths.find { it.value == snappedDate.shDay }?.index
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
                startIndex = months.find { it.value == startDate.shMonth }?.index ?: 0,
                onScrollFinished = { snappedIndex ->

                    val newMonth = months.find { it.index == snappedIndex }?.value

                    newMonth?.let {
                        val newDate = PersianDate(snappedDate.time)
                        newDate.shMonth = it
                        if (snappedDate.shDay > newDate.monthDays)
                            newDate.shDay = newDate.monthDays
                        val isDateBefore = newDate.before(startDate)

                        if (backwardsDisabled) {
                            if (!isDateBefore) {
                                snappedDate = newDate
                            }
                        } else {
                            snappedDate = newDate
                        }

                        dayOfMonths = calculateDayOfMonths(snappedDate.shMonth, snappedDate.shYear)

                        val newIndex = months.find { it.value == snappedDate.shMonth }?.index

                        newIndex?.let {
                            onSnappedDate(
                                JalaliSnappedDate.Month(
                                    localDate = snappedDate, index = newIndex
                                )
                            )?.let { return@WheelTextPicker it }
                        }
                    }


                    return@WheelTextPicker months.find { it.value == snappedDate.shMonth }?.index
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
                    startIndex = years.find { it.value == startDate.shYear }?.index ?: 0,
                    onScrollFinished = { snappedIndex ->

                        val newYear = years.find { it.index == snappedIndex }?.value

                        newYear?.let {
                            val newDate = PersianDate(snappedDate.time)
                            newDate.shYear = it
                            if (snappedDate.shDay > newDate.monthDays)
                                newDate.shDay = newDate.monthDays
                            val isDateBefore = newDate.before(startDate)

                            if (backwardsDisabled) {
                                if (!isDateBefore) {
                                    snappedDate = newDate
                                }
                            } else {
                                snappedDate = newDate
                            }

                            dayOfMonths = calculateDayOfMonths(snappedDate.shMonth, snappedDate.shYear)

                            val newIndex = years.find { it.value == snappedDate.shYear }?.index

                            newIndex?.let {
                                onSnappedDate(
                                    JalaliSnappedDate.Year(
                                        localDate = snappedDate, index = newIndex
                                    )
                                )?.let { return@WheelTextPicker it }

                            }
                        }

                        return@WheelTextPicker years.find { it.value == snappedDate.shYear }?.index
                    })
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun calculateDayOfMonths(month: Int, year: Int): List<DayOfMonth> {
    val monthLen = PersianDate().getMonthDays(year, month)
    Log.d("ahmad", PersianDate().monthList()[month - 1] + ":" + monthLen.toString())

    return (1..monthLen).map {
        DayOfMonth(
            text = it.toString(), value = it, index = it - 1
        )
    }
}