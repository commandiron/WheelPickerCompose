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

//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun WheelDatePicker(
//    modifier: Modifier = Modifier,
//    startDate: LocalDate = LocalDate.now(),
//    minYear: Int = 1922,
//    maxYear: Int = 2122,
//    disablePastDate: Boolean = false,
//    size: DpSize = DpSize(256.dp, 128.dp),
//    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
//    textColor: Color = LocalContentColor.current,
//    selectorEnabled: Boolean = true,
//    selectorShape: Shape = RoundedCornerShape(16.dp),
//    selectorColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
//    selectorBorder: BorderStroke? = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
//    onScrollFinished: (snappedDate: LocalDate) -> Unit = {},
//) {
//    val dayTexts = remember { mutableStateOf((1..31).toList().map { it.toString() }) }
//    val selectedDayOfMonth = remember { mutableStateOf(startDate.dayOfMonth)}
//
//    val monthTexts: List<String> = if(size.width / 3 < 55.dp){
//        DateFormatSymbols().shortMonths.toList()
//    }else{
//        DateFormatSymbols().months.toList()
//    }
//    val selectedMonth = remember { mutableStateOf(startDate.month.value)}
//
//    val years = IntRange(
//        start = minYear,
//        endInclusive = maxYear,
//    )
//    val yearTexts = years.map { it.toString() }
//    val selectedYear = remember { mutableStateOf(startDate.year)}
//
//    Box(modifier = modifier, contentAlignment = Alignment.Center){
//        if(selectorEnabled){
//            Surface(
//                modifier = Modifier
//                    .size(size.width, size.height / 3),
//                shape = selectorShape,
//                color = selectorColor,
//                border = selectorBorder
//            ) {}
//        }
//        Row {
//            WheelTextPicker(
//                size = DpSize(size.width / 3, size.height),
//                texts = dayTexts.value,
//                textStyle = textStyle,
//                textColor = textColor,
//                selectorEnabled = false,
//                startIndex = startDate.dayOfMonth - 1,
//                onScrollFinished = { selectedIndex ->
//                    try {
//
//                        val selectedDate = LocalDate.of(
//                            selectedYear.value,
//                            selectedMonth.value,
//                            selectedIndex + 1
//                        )
//                        val isDateBefore = isDateBefore(selectedDate, startDate)
//
//                        if(disablePastDate){
//                            if(!isDateBefore){
//                                selectedDayOfMonth.value = selectedIndex + 1
//                            }
//                        }else{
//                            selectedDayOfMonth.value = selectedIndex + 1
//                        }
//
//                        onScrollFinished(
//                            LocalDate.of(
//                                selectedYear.value,
//                                selectedMonth.value,
//                                selectedDayOfMonth.value
//                            )
//                        )
//                    }catch (e: Exception){
//                        e.printStackTrace()
//                    }
//                    return@WheelTextPicker selectedDayOfMonth.value - 1
//                }
//            )
//            WheelTextPicker(
//                size = DpSize(size.width / 3, size.height),
//                texts = monthTexts,
//                textStyle = textStyle,
//                textColor = textColor,
//                selectorEnabled = false,
//                startIndex = startDate.month.value - 1,
//                onScrollFinished = { selectedIndex ->
//
//                    dayTexts.value = calculateMonthDayTexts(selectedIndex + 1, selectedYear.value)
//
//                    try {
//                        val selectedDate = LocalDate.of(
//                            selectedYear.value,
//                            selectedIndex + 1,
//                            selectedDayOfMonth.value
//                        )
//
//                        val isDateBefore = isDateBefore(selectedDate, startDate)
//
//                        if(disablePastDate){
//                            if(!isDateBefore){
//                                selectedMonth.value = selectedIndex + 1
//                            }else{
//                                dayTexts.value = calculateMonthDayTexts(selectedMonth.value, selectedYear.value)
//                            }
//                        }else{
//                            selectedMonth.value = selectedIndex + 1
//                        }
//
//                        onScrollFinished(
//                            LocalDate.of(
//                                selectedYear.value,
//                                selectedMonth.value,
//                                selectedDayOfMonth.value
//                            )
//                        )
//                    }catch (e: Exception){
//                        selectedMonth.value = selectedIndex + 1
//                        e.printStackTrace()
//                    }
//                    return@WheelTextPicker selectedMonth.value - 1
//                }
//            )
//            WheelTextPicker(
//                size = DpSize(size.width / 3, size.height),
//                texts = yearTexts,
//                textStyle = textStyle,
//                textColor = textColor,
//                selectorEnabled = false,
//                startIndex = if(years.indexOf(years.find { it == startDate.year }) == -1) {
//                    throw IllegalArgumentException(
//                        "startDate.year should greater than minYear and smaller than maxYear"
//                    )
//                } else years.indexOf(years.find { it == startDate.year }),
//                onScrollFinished = { selectedIndex ->
//                    dayTexts.value = calculateMonthDayTexts(selectedMonth.value, yearTexts[selectedIndex].toInt())
//                    try {
//                        val selectedDate = LocalDate.of(
//                            yearTexts[selectedIndex].toInt(),
//                            selectedMonth.value,
//                            selectedDayOfMonth.value
//                        )
//
//                        val isDateBefore = isDateBefore(selectedDate, startDate)
//
//                        if(disablePastDate){
//                            if(!isDateBefore){
//                                selectedYear.value = yearTexts[selectedIndex].toInt()
//                            }
//                        }else{
//                            selectedYear.value = yearTexts[selectedIndex].toInt()
//                        }
//
//                        onScrollFinished(
//                            LocalDate.of(
//                                selectedYear.value,
//                                selectedMonth.value,
//                                selectedDayOfMonth.value
//                            )
//                        )
//                    }catch (e: Exception){
//                        selectedYear.value = yearTexts[selectedIndex].toInt()
//                        e.printStackTrace()
//                    }
//                    return@WheelTextPicker yearTexts.indexOf(selectedYear.value.toString())
//                }
//            )
//        }
//    }
//}
//
//@RequiresApi(Build.VERSION_CODES.O)
//private fun isDateBefore(date: LocalDate, currentDate: LocalDate): Boolean{
//    return date.isBefore(currentDate)
//}