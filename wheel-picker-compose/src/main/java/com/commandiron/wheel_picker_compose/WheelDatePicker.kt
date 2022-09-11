package com.commandiron.wheel_picker_compose

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import java.text.DateFormatSymbols
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WheelDatePicker(
    modifier: Modifier = Modifier,
    currentDate: LocalDate = LocalDate.now(),
    disableBackwards: Boolean = false,
    size: DpSize = DpSize(256.dp, 128.dp),
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textColor: Color = LocalContentColor.current,
    infiniteLoopEnabled: Boolean = false,
    selectorEnabled: Boolean = true,
    selectorShape: Shape = RoundedCornerShape(16.dp),
    selectorColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
    selectorBorder: BorderStroke? = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
    onScrollFinished : (snappedDate: LocalDate) -> Unit = {}
) {
    val dayTexts = remember { mutableStateOf((1..31).toList().map { it.toString() }) }
    val selectedDayOfMonth = remember { mutableStateOf(0)}

    val monthTexts: List<String> = if(size.width < 250.dp){
        DateFormatSymbols().shortMonths.toList()
    }else{
        DateFormatSymbols().months.toList()
    }
    val selectedMonth = remember { mutableStateOf(0)}

    var yearTexts = listOf<String>()
    val yearRange = 100
    for(i in 0 until (yearRange * 2) + 1){
        yearTexts = yearTexts + (currentDate.year - yearRange + i).toString()
    }
    val selectedYear = remember { mutableStateOf(0)}

    Box(modifier = modifier, contentAlignment = Alignment.Center){
        if(selectorEnabled){
            Surface(
                modifier = Modifier
                    .size(size.width, size.height / 3),
                shape = selectorShape,
                color = selectorColor,
                border = selectorBorder
            ) {}
        }
        Row {
            WheelTextPicker(
                size = DpSize(size.width / 3, size.height),
                texts = dayTexts.value,
                textStyle = textStyle,
                textColor = textColor,
                infiniteLoopEnabled = infiniteLoopEnabled,
                selectorEnabled = false,
                startIndex = currentDate.dayOfMonth - 1,
                onScrollFinished = { selectedIndex ->
                    selectedDayOfMonth.value =
                        if(disableBackwards){
                            if(selectedIndex < currentDate.dayOfMonth - 1 ) {
                                currentDate.dayOfMonth
                            }else{
                                selectedIndex + 1
                            }
                        }else{
                            selectedIndex + 1
                        }
                    try {
                        onScrollFinished(
                            LocalDate.of(
                                selectedYear.value,
                                selectedMonth.value,
                                selectedDayOfMonth.value
                            )
                        )
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                    if(disableBackwards && selectedIndex < currentDate.dayOfMonth - 1 ) {
                        return@WheelTextPicker currentDate.dayOfMonth - 1
                    }else{
                        return@WheelTextPicker selectedIndex
                    }
                }
            )
            WheelTextPicker(
                size = DpSize(size.width / 3, size.height),
                texts = monthTexts,
                textStyle = textStyle,
                textColor = textColor,
                infiniteLoopEnabled = infiniteLoopEnabled,
                selectorEnabled = false,
                startIndex = currentDate.month.value - 1,
                onScrollFinished = { selectedIndex ->
                    selectedMonth.value =
                        if(disableBackwards){
                            if(selectedIndex < currentDate.month.value - 1 ) {
                                currentDate.month.value
                            }else{
                                selectedIndex + 1
                            }
                        }else{
                            selectedIndex + 1
                        }
                    dayTexts.value = calculateMonthDayTexts(selectedMonth.value, selectedYear.value)
                    try {
                        onScrollFinished(
                            LocalDate.of(
                                selectedYear.value,
                                selectedMonth.value,
                                selectedDayOfMonth.value
                            )
                        )
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                    if(disableBackwards && selectedIndex < currentDate.month.value - 1 ) {
                        return@WheelTextPicker currentDate.month.value - 1
                    }else{
                        return@WheelTextPicker selectedIndex
                    }
                }
            )
            WheelTextPicker(
                size = DpSize(size.width / 3, size.height),
                texts = yearTexts,
                textStyle = textStyle,
                textColor = textColor,
                infiniteLoopEnabled = infiniteLoopEnabled,
                selectorEnabled = false,
                startIndex = yearRange,
                onScrollFinished = { selectedIndex ->
                    selectedYear.value =
                        if(disableBackwards){
                            if(yearTexts[selectedIndex].toInt() < currentDate.year ) {
                                yearTexts[yearRange].toInt()
                            }else{
                                yearTexts[selectedIndex].toInt()
                            }
                        }else{
                            yearTexts[selectedIndex].toInt()
                        }
                    dayTexts.value = calculateMonthDayTexts(selectedMonth.value, selectedYear.value)
                    try {
                        onScrollFinished(
                            LocalDate.of(
                                selectedYear.value,
                                selectedMonth.value,
                                selectedDayOfMonth.value
                            )
                        )
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                    if(disableBackwards && yearTexts[selectedIndex].toInt() < currentDate.year ) {
                        return@WheelTextPicker yearRange
                    }else{
                        return@WheelTextPicker selectedIndex
                    }
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun calculateMonthDayTexts(month: Int, year: Int): List<String> {

    val isLeapYear = LocalDate.of(year, month, 1).isLeapYear

    val month31day = (1..31).toList().map { it.toString() }
    val month30day = (1..30).toList().map { it.toString() }
    val month29day = (1..29).toList().map { it.toString() }
    val month28day = (1..28).toList().map { it.toString() }

    return when(month){
        1 -> { month31day }
        2 -> { if(isLeapYear) month29day else month28day }
        3 -> { month31day }
        4 -> { month30day }
        5 -> { month31day }
        6 -> { month30day }
        7 -> { month31day }
        8 -> { month31day }
        9 -> { month30day }
        10 -> { month31day }
        11 -> { month30day }
        12 -> { month31day }
        else -> { emptyList() }
    }
}