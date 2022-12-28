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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WheelDateTimePicker(
    modifier: Modifier = Modifier,
    startDateTime: LocalDateTime = LocalDateTime.now(),
    yearsRange: IntRange? = null,
    timeFormat: TimeFormat = TimeFormat.HOUR_24,
    backwardsDisabled: Boolean = false,
    size: DpSize = DpSize(256.dp, 128.dp),
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textColor: Color = LocalContentColor.current,
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
    calendarSystem: CalendarSystem = CalendarSystem.GREGORIAN,
    onSnappedDateTime: (snappedDateTime: LocalDateTime) -> Unit = {}
) {
    when (calendarSystem) {
        CalendarSystem.GREGORIAN -> DefaultWheelDateTimePicker(modifier,
            startDateTime,
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
            })

        CalendarSystem.JALALI -> JalaliWheelDateTimePicker(modifier,
            startDateTime,
            yearsRange ,
            timeFormat,
            backwardsDisabled,
            size,
            textStyle,
            textColor,
            selectorProperties,
            onSnappedDateTime = { snappedDateTime ->
                onSnappedDateTime(snappedDateTime.snappedLocalDateTime)
                snappedDateTime.snappedIndex
            })
    }

}