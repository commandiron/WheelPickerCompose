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
import com.commandiron.wheel_picker_compose.core.DefaultWheelDatePicker
import com.commandiron.wheel_picker_compose.core.JalaliWheelDatePicker
import com.commandiron.wheel_picker_compose.core.persianDate.PersianDate
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WheelDatePicker(
    modifier: Modifier = Modifier,
    startDate: LocalDate = LocalDate.now(),
    yearsRange: IntRange? = null,
    backwardsDisabled: Boolean = false,
    size: DpSize = DpSize(256.dp, 128.dp),
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textColor: Color = LocalContentColor.current,
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
    calendarSystem: CalendarSystem = CalendarSystem.GREGORIAN,
    onSnappedDate: (snappedDate: LocalDate) -> Unit = {}
) {
    when (calendarSystem) {
        CalendarSystem.GREGORIAN -> DefaultWheelDatePicker(modifier,
            startDate,
            yearsRange ?: IntRange(1922, 2122),
            backwardsDisabled,
            size,
            textStyle,
            textColor,
            selectorProperties,
            onSnappedDate = { snappedDate ->
                onSnappedDate(snappedDate.snappedLocalDate)
                snappedDate.snappedIndex
            })

        CalendarSystem.JALALI -> JalaliWheelDatePicker(modifier,
            PersianDate(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant())),
            yearsRange ?: IntRange(1300, 1500),
            backwardsDisabled,
            size,
            textStyle,
            textColor,
            selectorProperties,
            onSnappedDate = { snappedDate ->
                val localDate = LocalDate.parse(
                    SimpleDateFormat("yyyy-MM-dd").format(snappedDate.snappedLocalDate.toDate())
                )
                onSnappedDate(localDate)
                snappedDate.snappedIndex
            })

    }
}