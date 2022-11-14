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
import java.text.DateFormatSymbols
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WheelDatePicker(
    modifier: Modifier = Modifier,
    startDate: LocalDate = LocalDate.now(),
    minYear: Int = 1922,
    maxYear: Int = 2122,
    backwardsDisabled: Boolean = false,
    size: DpSize = DpSize(256.dp, 128.dp),
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textColor: Color = LocalContentColor.current,
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
    onSnappedDateIndex : (snappedDateIndex: Int) -> Int? = { null },
    onSnappedDate: (snappedDate: LocalDate) -> Unit = {},
) {
    val dayTexts = remember { mutableStateOf((1..31).toList().map { it.toString() }) }
    val monthTexts: List<String> = if(size.width / 3 < 55.dp){
        DateFormatSymbols().shortMonths.toList()
    }else{
        DateFormatSymbols().months.toList()
    }

    val years = IntRange(minYear, maxYear)
    val yearTexts = years.map { it.toString() }

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
            //Day of Month
            WheelTextPicker(
                size = DpSize(size.width / 3, size.height),
                texts = dayTexts.value,
                style = textStyle,
                color = textColor,
                selectorProperties = WheelPickerDefaults.selectorProperties(
                    enabled = false
                ),
                startIndex = startDate.dayOfMonth - 1,
                onScrollFinished = { snappedIndex ->

                    val newDate = snappedDate.withDayOfMonth(snappedIndex + 1)

                    val isDateBefore = isDateBefore(newDate, startDate)

                    if(backwardsDisabled) {
                        if(!isDateBefore) {
                            snappedDate = newDate
                        }
                    } else {
                        snappedDate = newDate
                    }

                    onSnappedDateIndex(snappedDate.dayOfMonth - 1)?.let { return@WheelTextPicker it }
                    onSnappedDate(snappedDate)

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

                    val newDate = snappedDate.withMonth(snappedIndex + 1)

                    val isDateBefore = isDateBefore(newDate, startDate)

                    if(backwardsDisabled) {
                        if(!isDateBefore) {
                            snappedDate = newDate
                        }
                    } else {
                        snappedDate = newDate
                    }

                    dayTexts.value = calculateMonthDayTexts(snappedDate.month.value, snappedDate.year)

                    onSnappedDateIndex(snappedDate.month.value - 1)?.let { return@WheelTextPicker it }
                    onSnappedDate(snappedDate)

                    return@WheelTextPicker snappedDate.month.value - 1
                }
            )
            //Year
            WheelTextPicker(
                size = DpSize(size.width / 3, size.height),
                texts = yearTexts,
                style = textStyle,
                color = textColor,
                selectorProperties = WheelPickerDefaults.selectorProperties(
                    enabled = false
                ),
                startIndex = if(years.indexOf(years.find { it == startDate.year }) == -1) {
                    throw IllegalArgumentException(
                        "startDate.year should greater than minYear and smaller than maxYear"
                    )
                } else years.indexOf(years.find { it == startDate.year }),
                onScrollFinished = { snappedIndex ->

                    val newDate = snappedDate.withYear(yearTexts[snappedIndex].toInt())

                    val isDateBefore = isDateBefore(newDate, startDate)

                    if(backwardsDisabled) {
                        if(!isDateBefore) {
                            snappedDate = newDate
                        }
                    } else {
                        snappedDate = newDate
                    }

                    dayTexts.value = calculateMonthDayTexts(snappedDate.month.value, snappedDate.year)

                    onSnappedDateIndex(yearTexts.indexOf(snappedDate.year.toString()))?.let { return@WheelTextPicker it }
                    onSnappedDate(snappedDate)

                    return@WheelTextPicker yearTexts.indexOf(snappedDate.year.toString())
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun isDateBefore(date: LocalDate, currentDate: LocalDate): Boolean{
    return date.isBefore(currentDate)
}