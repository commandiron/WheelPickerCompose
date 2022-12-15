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
import com.commandiron.wheel_picker_compose.core.DefaultWheelTimePicker
import java.time.LocalTime
import java.util.*

@Composable
@RequiresApi(Build.VERSION_CODES.O)
fun WheelTimePicker(
    modifier: Modifier = Modifier,
    startTime: LocalTime = LocalTime.now(),
    timeFormat: TimeFormat = TimeFormat.HOUR_24,
    backwardsDisabled: Boolean = false,
    size: DpSize = DpSize(128.dp, 128.dp),
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textColor: Color = LocalContentColor.current,
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
    onSnappedTime : (snappedTime: CompatTime) -> Unit = {},
) {
    DefaultWheelTimePicker(
        modifier,
        CompatTime(startTime.hour, startTime.minute),
        timeFormat,
        backwardsDisabled,
        size,
        textStyle,
        textColor,
        selectorProperties,
        onSnappedTime = { snappedTime, _ ->
            onSnappedTime(snappedTime.snappedLocalTime)
            snappedTime.snappedIndex
        }
    )
}

@Composable
fun WheelTimePickerCompat(
    modifier: Modifier = Modifier,
    startTime: Calendar = Calendar.getInstance(),
    timeFormat: TimeFormat = TimeFormat.HOUR_24,
    backwardsDisabled: Boolean = false,
    size: DpSize = DpSize(128.dp, 128.dp),
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textColor: Color = LocalContentColor.current,
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
    onSnappedTime : (snappedTime: CompatTime) -> Unit = {},
) {
    DefaultWheelTimePicker(
        modifier,
        CompatTime(startTime.get(Calendar.HOUR_OF_DAY), startTime.get(Calendar.MINUTE)),
        timeFormat,
        backwardsDisabled,
        size,
        textStyle,
        textColor,
        selectorProperties,
        onSnappedTime = { snappedTime, _ ->
            onSnappedTime(snappedTime.snappedLocalTime)
            snappedTime.snappedIndex
        }
    )
}