package com.commandiron.wheel_picker_compose.core

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import java.text.DateFormatSymbols
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
internal fun DefaultWheelDateWithoutYearPicker(
    modifier: Modifier = Modifier,
    startDate: LocalDate = LocalDate.now(),
    backwardsDisabled: Boolean = false,
    size: DpSize = DpSize(256.dp, 128.dp),
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textColor: Color = LocalContentColor.current,
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
    onSnappedDate : (snappedDate: SnappedDate) -> Int? = { _ -> null }
) {
    var dayTexts by remember { mutableStateOf((1..31).toList().map { it.toString() }) }
    val monthTexts: List<String> = if(size.width / 3 < 55.dp){
        DateFormatSymbols().shortMonths.toList()
    }else{
        DateFormatSymbols().months.toList()
    }

    var snappedDate by remember { mutableStateOf(startDate) }

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
            Spacer(Modifier.width(size.width / 6))
            //Day of Month
            WheelTextPicker(
                size = DpSize(size.width / 6, size.height),
                texts = dayTexts,
                style = textStyle,
                color = textColor,
                selectorProperties = WheelPickerDefaults.selectorProperties(
                    enabled = false
                ),
                startIndex = startDate.dayOfMonth - 1,
                onScrollFinished = { snappedIndex ->

                    val newDate = snappedDate.withDayOfMonth(if(snappedIndex + 1 > dayTexts.size) snappedIndex else snappedIndex + 1)
                    val isDateBefore = isDateBefore(newDate, startDate)

                    if(backwardsDisabled) {
                        if(!isDateBefore) {
                            snappedDate = newDate
                        }
                    } else {
                        snappedDate = newDate
                    }

                    onSnappedDate(
                        SnappedDate.DayOfMonth(snappedDate, snappedDate.dayOfMonth - 1)
                    )?.let { return@WheelTextPicker it }

                    return@WheelTextPicker snappedDate.dayOfMonth - 1
                }
            )
            //Month
            WheelTextPicker(
                size = DpSize(size.width / 3, size.height),
                texts = monthTexts,
                style = textStyle,
                color = textColor,
                selectorProperties = WheelPickerDefaults.selectorProperties(
                    enabled = false
                ),
                startIndex = startDate.month.value - 1,
                onScrollFinished = { snappedIndex ->

                    val newDate = snappedDate.withMonth(if(snappedIndex + 1 > 12) 12 else snappedIndex + 1)
                    val isDateBefore = isDateBefore(newDate, startDate)

                    if(backwardsDisabled) {
                        if(!isDateBefore) {
                            snappedDate = newDate
                        }
                    } else {
                        snappedDate = newDate
                    }

                    dayTexts = calculateMonthDayTexts(snappedDate.month.value, snappedDate.year)

                    onSnappedDate(
                        SnappedDate.Month(snappedDate, snappedDate.month.value - 1)
                    )?.let { return@WheelTextPicker it }

                    return@WheelTextPicker snappedDate.month.value - 1
                }
            )
            Spacer(Modifier.width(size.width / 6))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun isDateBefore(date: LocalDate, currentDate: LocalDate): Boolean{
    return date.isBefore(currentDate)
}