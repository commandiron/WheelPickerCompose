package com.commandiron.wheel_picker_compose

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.commandiron.wheel_picker_compose.core.*
import com.commandiron.wheel_picker_compose.core.DefaultWheelDateTimePicker
import java.time.LocalDateTime
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WheelDateTimePicker(
    modifier: Modifier = Modifier,
    startDateTime: LocalDateTime = LocalDateTime.now(),
    yearsRange: IntRange? = IntRange(1922, 2122),
    timeFormat: TimeFormat = TimeFormat.HOUR_24,
    backwardsDisabled: Boolean = false,
    size: DpSize = DpSize(256.dp, 128.dp),
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textColor: Color = LocalContentColor.current,
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
    onSnappedDateTime: (snappedDateTime: CompatDateTime) -> Unit = {}
) {
    DefaultWheelDateTimePicker(
        modifier,
        CompatDateTime(
            CompatDate(startDateTime.dayOfMonth, startDateTime.monthValue, startDateTime.year),
            CompatTime(startDateTime.hour, startDateTime.minute)
        ),
        yearsRange,
        timeFormat,
        backwardsDisabled,
        size,
        textStyle,
        textColor,
        selectorProperties,
        onSnappedDateTime = { snappedDateTime ->
            onSnappedDateTime(snappedDateTime.snappedLocalDateTime)
            snappedDateTime.snappedIndex
        }
    )
}

@Composable
fun WheelDateTimePickerCompat(
    modifier: Modifier = Modifier,
    startDateTime: Calendar = Calendar.getInstance(),
    yearsRange: IntRange? = IntRange(1922, 2122),
    timeFormat: TimeFormat = TimeFormat.HOUR_24,
    backwardsDisabled: Boolean = false,
    size: DpSize = DpSize(256.dp, 128.dp),
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textColor: Color = LocalContentColor.current,
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
    onSnappedDateTime: (snappedDateTime: CompatDateTime) -> Unit = {}
) {
    DefaultWheelDateTimePicker(
        modifier,
        CompatDateTime(
            CompatDate(
                startDateTime.get(Calendar.DAY_OF_MONTH),
                startDateTime.get(Calendar.MONTH),
                startDateTime.get(Calendar.YEAR)
            ),
            CompatTime(startDateTime.get(Calendar.HOUR), startDateTime.get(Calendar.MINUTE))
        ),
        yearsRange,
        timeFormat,
        backwardsDisabled,
        size,
        textStyle,
        textColor,
        selectorProperties,
        onSnappedDateTime = { snappedDateTime ->
            onSnappedDateTime(snappedDateTime.snappedLocalDateTime)
            snappedDateTime.snappedIndex
        }
    )
}