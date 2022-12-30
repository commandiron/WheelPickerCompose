package com.commandiron.wheel_picker_compose.core

import android.os.Build
import androidx.annotation.RequiresApi
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
import com.commandiron.wheel_picker_compose.core.persianDate.PersianDate
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
internal fun JalaliWheelDateTimePicker(
    modifier: Modifier = Modifier,
    startDateTime: LocalDateTime = LocalDateTime.now(),
    yearsRange: IntRange? = IntRange(1300, 1500),
    timeFormat: TimeFormat = TimeFormat.HOUR_24,
    backwardsDisabled: Boolean = false,
    size: DpSize = DpSize(256.dp, 128.dp),
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textColor: Color = LocalContentColor.current,
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
    onSnappedDateTime: (snappedDateTime: SnappedDateTime) -> Int? = { _ -> null }
) {

    var snappedDateTime by remember { mutableStateOf(startDateTime.truncatedTo(ChronoUnit.MINUTES)) }

    val yearTexts = yearsRange?.map { it.toString() } ?: listOf()

    val jalaliStartDate = PersianDate(
        Date.from(
            startDateTime.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant()
        )
    )
    Box(
        modifier = modifier, contentAlignment = Alignment.Center
    ) {
        if (selectorProperties.enabled().value) {
            Surface(
                modifier = Modifier.size(size.width, size.height / 3),
                shape = selectorProperties.shape().value,
                color = selectorProperties.color().value,
                border = selectorProperties.border().value
            ) {}
        }
        Row {
            //Date
            JalaliWheelDatePicker(
                startDate = jalaliStartDate,
                yearsRange = yearsRange,
                backwardsDisabled = backwardsDisabled,
                size = DpSize(
                    width = if (yearsRange == null) size.width * 3 / 6 else size.width * 3 / 5, height = size.height
                ),
                textStyle = textStyle,
                textColor = textColor,
                selectorProperties = WheelPickerDefaults.selectorProperties(
                    enabled = false
                ),
                onSnappedDate = { snappedDate ->
                    val snappedLocalDate = LocalDate.parse(
                        SimpleDateFormat("yyyy-MM-dd").format(snappedDate.snappedLocalDate.toDate())
                    )

                    val newDateTime = when (snappedDate) {
                        is JalaliSnappedDate.DayOfMonth -> {
                            snappedDateTime.withDayOfMonth(snappedLocalDate.dayOfMonth)
                        }

                        is JalaliSnappedDate.Month -> {
                            snappedDateTime.withMonth(snappedLocalDate.monthValue)
                        }

                        is JalaliSnappedDate.Year -> {
                            snappedDateTime.withYear(snappedLocalDate.year)
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

                    return@JalaliWheelDatePicker when (snappedDate) {
                        is JalaliSnappedDate.DayOfMonth -> {
                            onSnappedDateTime(
                                SnappedDateTime.DayOfMonth(
                                    snappedDateTime, snappedDateTime.dayOfMonth - 1
                                )
                            )
                            snappedDate.localDate.shDay - 1
                        }

                        is JalaliSnappedDate.Month -> {
                            onSnappedDateTime(
                                SnappedDateTime.Month(
                                    snappedDateTime, snappedDateTime.month.value - 1
                                )
                            )
                            snappedDate.localDate.shMonth - 1
                        }

                        is JalaliSnappedDate.Year -> {
                            onSnappedDateTime(
                                SnappedDateTime.Year(
                                    snappedDateTime, yearTexts.indexOf(snappedDateTime.year.toString())
                                )
                            )

                            yearTexts.indexOf(snappedDate.localDate.shYear.toString())
                        }
                    }
                })
            //Time
            DefaultWheelTimePicker(startTime = startDateTime.toLocalTime(),
                timeFormat = timeFormat,
                backwardsDisabled = false,
                size = DpSize(
                    width = if (yearsRange == null) size.width * 3 / 6 else size.width * 2 / 5, height = size.height
                ),
                textStyle = textStyle,
                textColor = textColor,
                selectorProperties = WheelPickerDefaults.selectorProperties(
                    enabled = false
                ),
                onSnappedTime = { snappedTime, timeFormat ->

                    val newDateTime = when (snappedTime) {
                        is SnappedTime.Hour -> {
                            snappedDateTime.withHour(snappedTime.snappedLocalTime.hour)
                        }

                        is SnappedTime.Minute -> {
                            snappedDateTime.withMinute(snappedTime.snappedLocalTime.minute)
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
                            onSnappedDateTime(SnappedDateTime.Hour(snappedDateTime, snappedDateTime.hour))
                            if (timeFormat == TimeFormat.HOUR_24) snappedDateTime.hour else localTimeToAmPmHour(
                                snappedDateTime.toLocalTime()
                            ) - 1
                        }

                        is SnappedTime.Minute -> {
                            onSnappedDateTime(SnappedDateTime.Minute(snappedDateTime, snappedDateTime.minute))
                            snappedDateTime.minute
                        }
                    }
                })
        }
    }
}

private fun isDateTimeBefore(date: LocalDateTime, currentDateTime: LocalDateTime): Boolean {
    return date.isBefore(currentDateTime)
}












