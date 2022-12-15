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
import com.commandiron.wheel_picker_compose.core.CompatDate
import com.commandiron.wheel_picker_compose.core.DefaultWheelDatePicker
import com.commandiron.wheel_picker_compose.core.SelectorProperties
import com.commandiron.wheel_picker_compose.core.WheelPickerDefaults
import java.time.LocalDate
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WheelDatePicker(
    modifier: Modifier = Modifier,
    startDate: LocalDate = LocalDate.now(),
    yearsRange: IntRange? = IntRange(1922, 2122),
    backwardsDisabled: Boolean = false,
    size: DpSize = DpSize(256.dp, 128.dp),
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textColor: Color = LocalContentColor.current,
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
    onSnappedDate: (snappedDate: CompatDate) -> Unit = {}
) {
    DefaultWheelDatePicker(
        modifier,
        CompatDate(startDate.dayOfMonth, startDate.monthValue, startDate.year),
        yearsRange,
        backwardsDisabled,
        size,
        textStyle,
        textColor,
        selectorProperties,
        onSnappedDate = { snappedDate ->
            onSnappedDate(snappedDate.snappedLocalDate)
            snappedDate.snappedIndex
        }
    )
}

@Composable
fun WheelDatePickerCompat(
    modifier: Modifier = Modifier,
    startDate: Calendar = Calendar.getInstance(),
    yearsRange: IntRange? = IntRange(1922, 2122),
    backwardsDisabled: Boolean = false,
    size: DpSize = DpSize(256.dp, 128.dp),
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textColor: Color = LocalContentColor.current,
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
    onSnappedDate: (snappedDate: CompatDate) -> Unit = {}
) {
    DefaultWheelDatePicker(
        modifier,
        CompatDate(
            startDate.get(Calendar.DAY_OF_MONTH),
            startDate.get(Calendar.MONTH),
            startDate.get(Calendar.YEAR)
        ),
        yearsRange,
        backwardsDisabled,
        size,
        textStyle,
        textColor,
        selectorProperties,
        onSnappedDate = { snappedDate ->
            onSnappedDate(snappedDate.snappedLocalDate)
            snappedDate.snappedIndex
        }
    )
}