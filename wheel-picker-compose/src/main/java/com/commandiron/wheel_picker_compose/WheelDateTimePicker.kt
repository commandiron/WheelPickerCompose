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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import java.time.LocalDateTime
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WheelDateTimePicker(
    modifier: Modifier = Modifier,
    currentDateTime: LocalDateTime = LocalDateTime.now(),
    disablePastDateTime: Boolean = false,
    size: DpSize = DpSize(256.dp, 128.dp),
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textColor: Color = LocalContentColor.current,
    selectorEnabled: Boolean = true,
    selectorShape: Shape = RoundedCornerShape(16.dp),
    selectorColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
    selectorBorder: BorderStroke? = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
    onScrollFinished : (snappedDateTime: LocalDateTime) -> Unit = {}
) {
    val dayTexts = remember { mutableStateOf((1..31).toList().map { it.toString() }) }
    val selectedDayOfMonth = remember { mutableStateOf(currentDateTime.dayOfMonth)}

    val monthTexts: List<String> = if(size.width / 5 < 55.dp){
        DateFormatSymbols().shortMonths.toList()
    }else{
        DateFormatSymbols().months.toList()
    }
    val selectedMonth = remember { mutableStateOf(currentDateTime.month.value)}

    val yearRange = 100
    val yearTexts = IntRange(
        start = currentDateTime.year - yearRange,
        endInclusive = currentDateTime.year + yearRange
    ).map { it.toString() }
    val selectedYear = remember { mutableStateOf(currentDateTime.year)}

    val hourTexts: List<String> = (0..23).map { it.toString().padStart(2, '0') }
    val selectedHour = remember { mutableStateOf(currentDateTime.hour) }

    val minuteTexts: List<String> = (0..59).map { it.toString().padStart(2, '0') }
    val selectedMinute = remember { mutableStateOf(currentDateTime.minute) }

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
                size = DpSize(size.width / 5, size.height),
                texts = dayTexts.value,
                textStyle = textStyle,
                textColor = textColor,
                selectorEnabled = false,
                startIndex = currentDateTime.dayOfMonth - 1,
                onScrollFinished = { selectedIndex ->
                    try {
                        val selectedDateTime = LocalDateTime.of(
                            selectedYear.value,
                            selectedMonth.value,
                            selectedIndex + 1,
                            selectedHour.value,
                            selectedMinute.value
                        )
                        val isDateTimeBefore = isDateTimeBefore(selectedDateTime, currentDateTime)

                        if(disablePastDateTime){
                            if(!isDateTimeBefore){
                                selectedDayOfMonth.value = selectedIndex + 1
                            }
                        }else{
                            selectedDayOfMonth.value = selectedIndex + 1
                        }

                        onScrollFinished(
                            LocalDateTime.of(
                                selectedYear.value,
                                selectedMonth.value,
                                selectedDayOfMonth.value,
                                selectedHour.value,
                                selectedMinute.value
                            )
                        )
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                    return@WheelTextPicker selectedDayOfMonth.value - 1
                }
            )
            WheelTextPicker(
                size = DpSize(size.width / 5, size.height),
                texts = monthTexts,
                textStyle = textStyle,
                textColor = textColor,
                selectorEnabled = false,
                startIndex = currentDateTime.month.value - 1,
                onScrollFinished = { selectedIndex ->

                    dayTexts.value = calculateMonthDayTexts(selectedIndex + 1, selectedYear.value)

                    try {
                        val selectedDateTime = LocalDateTime.of(
                            selectedYear.value,
                            selectedIndex + 1,
                            selectedDayOfMonth.value,
                            selectedHour.value,
                            selectedMinute.value
                        )

                        val isDateTimeBefore = isDateTimeBefore(selectedDateTime, currentDateTime)

                        if(disablePastDateTime){
                            if(!isDateTimeBefore){
                                selectedMonth.value = selectedIndex + 1
                            }
                        }else{
                            selectedMonth.value = selectedIndex + 1
                        }

                        onScrollFinished(
                            LocalDateTime.of(
                                selectedYear.value,
                                selectedMonth.value,
                                selectedDayOfMonth.value,
                                selectedHour.value,
                                selectedMinute.value
                            )
                        )
                    }catch (e: Exception){
                        selectedMonth.value = selectedIndex + 1
                        e.printStackTrace()
                    }
                    return@WheelTextPicker selectedMonth.value - 1
                }
            )
            WheelTextPicker(
                size = DpSize(size.width / 5, size.height),
                texts = yearTexts,
                textStyle = textStyle,
                textColor = textColor,
                selectorEnabled = false,
                startIndex = yearRange,
                onScrollFinished = { selectedIndex ->
                    dayTexts.value = calculateMonthDayTexts(selectedMonth.value, yearTexts[selectedIndex].toInt())
                    try {
                        val selectedDateTime = LocalDateTime.of(
                            yearTexts[selectedIndex].toInt(),
                            selectedMonth.value,
                            selectedDayOfMonth.value,
                            selectedHour.value,
                            selectedMinute.value
                        )

                        val isDateTimeBefore = isDateTimeBefore(selectedDateTime, currentDateTime)

                        if(disablePastDateTime){
                            if(!isDateTimeBefore){
                                selectedYear.value = yearTexts[selectedIndex].toInt()
                            }
                        }else{
                            selectedYear.value = yearTexts[selectedIndex].toInt()
                        }

                        onScrollFinished(
                            LocalDateTime.of(
                                selectedYear.value,
                                selectedMonth.value,
                                selectedDayOfMonth.value,
                                selectedHour.value,
                                selectedMinute.value
                            )
                        )
                    }catch (e: Exception){
                        selectedYear.value = yearTexts[selectedIndex].toInt()
                        e.printStackTrace()
                    }
                    return@WheelTextPicker yearTexts.indexOf(selectedYear.value.toString())
                }
            )
            Box(Modifier.size(width = size.width * 2 / 5, height = size.height)) {
                Row() {
                    WheelTextPicker(
                        size = DpSize(size.width / 5, size.height),
                        texts = hourTexts,
                        textStyle = textStyle,
                        textColor = textColor,
                        startIndex = currentDateTime.hour,
                        selectorEnabled = false,
                        onScrollFinished = { selectedIndex ->
                            try {

                                val selectedDateTime = LocalDateTime.of(
                                    selectedYear.value,
                                    selectedMonth.value,
                                    selectedDayOfMonth.value,
                                    selectedIndex,
                                    selectedMinute.value
                                )
                                val isDateTimeBefore = isDateTimeBefore(selectedDateTime, currentDateTime)

                                if(disablePastDateTime){
                                    if(!isDateTimeBefore){
                                        selectedHour.value = selectedIndex
                                    }
                                }else{
                                    selectedHour.value = selectedIndex
                                }

                                onScrollFinished(
                                    LocalDateTime.of(
                                        selectedYear.value,
                                        selectedMonth.value,
                                        selectedDayOfMonth.value,
                                        selectedHour.value,
                                        selectedMinute.value
                                    )
                                )
                            }catch (e: Exception){
                                e.printStackTrace()
                            }

                            return@WheelTextPicker selectedHour.value
                        }
                    )
                    WheelTextPicker(
                        size = DpSize(size.width / 5, size.height),
                        texts = minuteTexts,
                        textStyle = textStyle,
                        textColor = textColor,
                        startIndex = currentDateTime.minute,
                        selectorEnabled = false,
                        onScrollFinished = { selectedIndex ->
                            try {

                                val selectedDateTime = LocalDateTime.of(
                                    selectedYear.value,
                                    selectedMonth.value,
                                    selectedDayOfMonth.value,
                                    selectedHour.value,
                                    selectedIndex
                                )
                                val isDateTimeBefore = isDateTimeBefore(selectedDateTime, currentDateTime)

                                if(disablePastDateTime){
                                    if(!isDateTimeBefore){
                                        selectedMinute.value = selectedIndex
                                    }
                                }else{
                                    selectedMinute.value = selectedIndex
                                }

                                onScrollFinished(
                                    LocalDateTime.of(
                                        selectedYear.value,
                                        selectedMonth.value,
                                        selectedDayOfMonth.value,
                                        selectedHour.value,
                                        selectedMinute.value
                                    )
                                )
                            }catch (e: Exception){
                                e.printStackTrace()
                            }

                            return@WheelTextPicker selectedMinute.value
                        }
                    )
                }
                Box(
                    modifier = Modifier.size(size),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = ":",
                        style = textStyle,
                        color = textColor
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun isDateTimeBefore(date: LocalDateTime, currentDateTime: LocalDateTime): Boolean{
    return date.isBefore(currentDateTime)
}












