package com.commandiron.wheel_picker_compose

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
import com.commandiron.wheel_picker_compose.core.SelectorProperties
import com.commandiron.wheel_picker_compose.core.WheelPickerDefaults
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WheelDateTimePicker(
    modifier: Modifier = Modifier,
    startDateTime: LocalDateTime = LocalDateTime.now(),
    minYear: Int = 1922,
    maxYear: Int = 2122,
    backwardsDisabled: Boolean = false,
    size: DpSize = DpSize(256.dp, 128.dp),
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textColor: Color = LocalContentColor.current,
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
    onSnappedDateTime : (snappedDateTime: SnappedDateTime) -> Int? = { _ -> null }
) {

    var snappedDateTime by remember { mutableStateOf(startDateTime) }

    val yearTexts = IntRange(minYear, maxYear).map { it.toString() }

    Box(modifier = modifier, contentAlignment = Alignment.Center){
        if(selectorProperties.enabled().value){
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
            WheelDatePicker(
                startDate = startDateTime.toLocalDate(),
                minYear = minYear,
                maxYear = maxYear,
                backwardsDisabled = false,
                size = DpSize(size.width / 5 * 3, size.height),
                textStyle = textStyle,
                textColor = textColor,
                selectorProperties = WheelPickerDefaults.selectorProperties(
                    enabled = false
                ),
                onSnappedDate = { snappedDate ->

                    val newDateTime = snappedDateTime.with(snappedDate.snappedLocalDate)
                    val isDateTimeBefore = isDateTimeBefore(newDateTime, startDateTime)

                    if(backwardsDisabled) {
                        if(!isDateTimeBefore) {
                            snappedDateTime = newDateTime
                        }
                    } else {
                        snappedDateTime = newDateTime
                    }

                    return@WheelDatePicker when(snappedDate) {
                        is SnappedDate.DayOfMonth -> {
                            onSnappedDateTime(SnappedDateTime.DayOfMonth(snappedDateTime,snappedDateTime.dayOfMonth - 1))
                            snappedDateTime.dayOfMonth - 1
                        }
                        is SnappedDate.Month -> {
                            onSnappedDateTime(SnappedDateTime.Month(snappedDateTime,snappedDateTime.month.value - 1))
                            snappedDateTime.month.value - 1
                        }
                        is SnappedDate.Year -> {
                            onSnappedDateTime(SnappedDateTime.Year(snappedDateTime, yearTexts.indexOf(snappedDateTime.year.toString())))
                            yearTexts.indexOf(snappedDateTime.year.toString())
                        }
                    }
                }
            )
            //Time
            WheelTimePicker(
                startTime = startDateTime.toLocalTime(),
                backwardsDisabled = false,
                size = DpSize(size.width / 5 * 2, size.height),
                textStyle = textStyle,
                textColor = textColor,
                selectorProperties = WheelPickerDefaults.selectorProperties(
                    enabled = false
                ),
                onSnappedTime = { snappedTime ->

                    val newDateTime = snappedDateTime.with(snappedTime.snappedLocalTime)
                    val isDateTimeBefore = isDateTimeBefore(newDateTime, startDateTime)

                    if(backwardsDisabled) {
                        if(!isDateTimeBefore) {
                            snappedDateTime = newDateTime
                        }
                    } else {
                        snappedDateTime = newDateTime
                    }

                    return@WheelTimePicker when(snappedTime) {
                        is SnappedTime.Hour -> {
                            onSnappedDateTime(SnappedDateTime.Hour(snappedDateTime, snappedDateTime.hour))
                            snappedDateTime.hour
                        }
                        is SnappedTime.Minute -> {
                            onSnappedDateTime(SnappedDateTime.Minute(snappedDateTime, snappedDateTime.minute))
                            snappedDateTime.minute
                        }
                    }
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun isDateTimeBefore(date: LocalDateTime, currentDateTime: LocalDateTime): Boolean{
    return date.isBefore(currentDateTime)
}












