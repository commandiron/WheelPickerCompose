package com.commandiron.wheel_picker_compose.core

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
import java.security.acl.Group
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Composable
internal fun GroupedWheelDateTimePicker(
    modifier: Modifier = Modifier,
    startDateTime: LocalDateTime = LocalDateTime.now(),
    minDateTime: LocalDateTime = LocalDateTime.MIN,
    maxDateTime: LocalDateTime = LocalDateTime.MAX,
    todayLabel: String? = null,
    dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy"),
    timeFormat: TimeFormat = TimeFormat.HOUR_24,
    size: DpSize = DpSize(256.dp, 128.dp),
    rowCount: Int = 3,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textColor: Color = LocalContentColor.current,
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
    onDateTime : (snappedDateTime: GroupedDateTime) -> Unit = {}
) {
    var snappedDateTime by remember { mutableStateOf(startDateTime.truncatedTo(ChronoUnit.MINUTES)) }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        if (selectorProperties.enabled().value) {
            Surface(
                modifier = Modifier
                    .size(size.width, size.height / rowCount),
                shape = selectorProperties.shape().value,
                color = selectorProperties.color().value,
                border = selectorProperties.border().value
            ) {}
        }
        Row {
            //Date
            GroupedWheelDatePicker(
                startDate = startDateTime.toLocalDate(),
                minDate = minDateTime.toLocalDate(),
                maxDate = maxDateTime.toLocalDate(),
                todayLabel = todayLabel,
                format = dateFormat,
                size = DpSize(
                    width = size.width / 2,
                    height = size.height
                ),
                rowCount = rowCount,
                textStyle = textStyle,
                textColor = textColor,
                selectorProperties = WheelPickerDefaults.selectorProperties(
                    enabled = false
                ),
                onSnappedDate = { snappedDate ->
                    val newDateTime = when(snappedDate) {
                        is GroupedDate.Date -> {
                            snappedDateTime
                                .withYear(snappedDate.groupedLocalDate.year)
                                .withMonth(snappedDate.groupedLocalDate.monthValue)
                                .withDayOfMonth(snappedDate.groupedLocalDate.dayOfMonth)
                        }
                    }

                    if (!newDateTime.isBefore(minDateTime) && !newDateTime.isAfter(maxDateTime)) {
                        snappedDateTime = newDateTime
                    }

                    onDateTime(GroupedDateTime.Date(snappedDateTime))
                }
            )
            //Time
            DefaultWheelTimePicker(
                startTime = startDateTime.toLocalTime(),
                timeFormat = timeFormat,
                size = DpSize(
                    width = size.width / 2,
                    height = size.height
                ),
                rowCount = rowCount,
                textStyle = textStyle,
                textColor = textColor,
                selectorProperties = WheelPickerDefaults.selectorProperties(
                    enabled = false
                ),
                onSnappedTime = { snappedTime, timeFormat ->
                    val newDateTime = when(snappedTime) {
                        is SnappedTime.Hour -> {
                            snappedDateTime.withHour(snappedTime.snappedLocalTime.hour)
                        }
                        is SnappedTime.Minute -> {
                            snappedDateTime.withMinute(snappedTime.snappedLocalTime.minute)
                        }
                    }

                    if (!newDateTime.isBefore(minDateTime) && !newDateTime.isAfter(maxDateTime)) {
                        snappedDateTime = newDateTime
                    }

                    return@DefaultWheelTimePicker when(snappedTime) {
                        is SnappedTime.Hour -> {
                            onDateTime(GroupedDateTime.Hour(snappedDateTime))
                            if (timeFormat == TimeFormat.HOUR_24) snappedDateTime.hour else
                            localTimeToAmPmHour(snappedDateTime.toLocalTime()) - 1
                        }
                        is SnappedTime.Minute -> {
                            onDateTime(GroupedDateTime.Minute(snappedDateTime))
                            snappedDateTime.minute
                        }
                    }
                }
            )
        }
    }
}