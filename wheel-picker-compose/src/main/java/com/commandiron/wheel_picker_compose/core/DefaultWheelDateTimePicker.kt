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
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Composable
internal fun DefaultWheelDateTimePicker(
    modifier: Modifier = Modifier,
    startDateTime: CompatDateTime,
    yearsRange: IntRange? = IntRange(1922, 2122),
    timeFormat: TimeFormat = TimeFormat.HOUR_24,
    backwardsDisabled: Boolean = false,
    size: DpSize = DpSize(256.dp, 128.dp),
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textColor: Color = LocalContentColor.current,
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
    onSnappedDateTime: (snappedDateTime: SnappedDateTime) -> Int? = { _ -> null }
) {

    var snappedDateTime by remember { mutableStateOf(startDateTime) }

    val yearTexts = yearsRange?.map { it.toString() } ?: listOf()

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        if (selectorProperties.enabled().value) {
            Surface(
                modifier = Modifier
                    .size(size.width, size.height / 3),
                shape = selectorProperties.shape().value,
                color = selectorProperties.color().value,
                border = selectorProperties.border().value
            ) {}
        }
        Row {
            //Date
            DefaultWheelDatePicker(
                startDate = CompatDate(
                    startDateTime.compatDate.dayOfMonth,
                    startDateTime.compatDate.month,
                    startDateTime.compatDate.year
                ),
                yearsRange = yearsRange,
                backwardsDisabled = false,
                size = DpSize(
                    width = if (yearsRange == null) size.width * 3 / 6 else size.width * 3 / 5,
                    height = size.height
                ),
                textStyle = textStyle,
                textColor = textColor,
                selectorProperties = WheelPickerDefaults.selectorProperties(
                    enabled = false
                ),
                onSnappedDate = { snappedDate ->

                    val newDateTime = when (snappedDate) {
                        is SnappedDate.DayOfMonth -> {
                            snappedDateTime.copy(
                                compatDate = snappedDateTime.compatDate.copy(
                                    dayOfMonth = snappedDate.snappedLocalDate.dayOfMonth
                                )
                            )
                        }
                        is SnappedDate.Month -> {
                            snappedDateTime.copy(compatDate = snappedDateTime.compatDate.copy(month = snappedDate.snappedLocalDate.month))
                        }
                        is SnappedDate.Year -> {
                            snappedDateTime.copy(compatDate = snappedDateTime.compatDate.copy(year = snappedDate.snappedLocalDate.year))
                        }
                    }

                    val isDateTimeBefore = isDateTimeBefore(newDateTime, startDateTime)

                    if (backwardsDisabled) {
                        if (!isDateTimeBefore) {
                            snappedDateTime = newDateTime
                        }
                    } else {
                        snappedDateTime = newDateTime
                    }

                    return@DefaultWheelDatePicker when (snappedDate) {
                        is SnappedDate.DayOfMonth -> {
                            onSnappedDateTime(
                                SnappedDateTime.DayOfMonth(
                                    snappedDateTime,
                                    snappedDateTime.dayOfMonth - 1
                                )
                            )
                            snappedDateTime.dayOfMonth - 1
                        }
                        is SnappedDate.Month -> {
                            onSnappedDateTime(
                                SnappedDateTime.Month(
                                    snappedDateTime,
                                    snappedDateTime.month - 1
                                )
                            )
                            snappedDateTime.month - 1
                        }
                        is SnappedDate.Year -> {
                            onSnappedDateTime(
                                SnappedDateTime.Year(
                                    snappedDateTime,
                                    yearTexts.indexOf(snappedDateTime.year.toString())
                                )
                            )
                            yearTexts.indexOf(snappedDateTime.year.toString())
                        }
                    }
                }
            )
            //Time
            DefaultWheelTimePicker(
                startTime = CompatTime(
                    startDateTime.compatTime.hour,
                    startDateTime.compatTime.minute
                ),
                timeFormat = timeFormat,
                backwardsDisabled = false,
                size = DpSize(
                    width = if (yearsRange == null) size.width * 3 / 6 else size.width * 2 / 5,
                    height = size.height
                ),
                textStyle = textStyle,
                textColor = textColor,
                selectorProperties = WheelPickerDefaults.selectorProperties(
                    enabled = false
                ),
                onSnappedTime = { snappedTime, timeFormat ->

                    val newDateTime = when (snappedTime) {
                        is SnappedTime.Hour -> {
                            snappedDateTime.copy(compatTime = snappedDateTime.compatTime.copy(hour = snappedTime.snappedLocalTime.hour))
                        }
                        is SnappedTime.Minute -> {
                            snappedDateTime.copy(compatTime = snappedDateTime.compatTime.copy(minute = snappedTime.snappedLocalTime.minute))
                        }
                    }

                    val isDateTimeBefore = isDateTimeBefore(newDateTime, startDateTime)

                    if (backwardsDisabled) {
                        if (!isDateTimeBefore) {
                            snappedDateTime = newDateTime
                        }
                    } else {
                        snappedDateTime = newDateTime
                    }

                    return@DefaultWheelTimePicker when (snappedTime) {
                        is SnappedTime.Hour -> {
                            onSnappedDateTime(
                                SnappedDateTime.Hour(
                                    snappedDateTime,
                                    snappedDateTime.hour
                                )
                            )
                            if (timeFormat == TimeFormat.HOUR_24) snappedDateTime.hour else
                                localTimeToAmPmHour(
                                    CompatTime(
                                        snappedDateTime.hour,
                                        snappedDateTime.minute
                                    )
                                ) - 1
                        }
                        is SnappedTime.Minute -> {
                            onSnappedDateTime(
                                SnappedDateTime.Minute(
                                    snappedDateTime,
                                    snappedDateTime.minute
                                )
                            )
                            snappedDateTime.minute
                        }
                    }
                }
            )
        }
    }
}

private fun isDateTimeBefore(date: CompatDateTime, currentDateTime: CompatDateTime): Boolean {
    return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
        date.toLocalDateTime().isBefore(currentDateTime.toLocalDateTime())
    else date.toCalendarDateTime().before(currentDateTime.toCalendarDateTime())
}












