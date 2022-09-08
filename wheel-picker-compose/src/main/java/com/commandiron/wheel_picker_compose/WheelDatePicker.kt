package com.commandiron.wheel_picker_compose

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import java.text.DateFormatSymbols
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WheelDatePicker(
    modifier: Modifier = Modifier,
    size: DpSize = DpSize(256.dp, 128.dp),
    selectorShape: Shape = RoundedCornerShape(16.dp),
    selectorColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
    selectorBorder: BorderStroke? = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
    onScrollFinished : (snappedDate: LocalDate?) -> Unit
) {
    val localDateNow = LocalDate.now()

    val dayTexts = remember {
        mutableStateOf(
            listOf(
                "1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17",
                "18","19","20","21","22","23","24","25","26","27","28","29","30","31"
            )
        )
    }
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
        yearTexts = yearTexts + (localDateNow.year - yearRange + i).toString()
    }
    val selectedYear = remember { mutableStateOf(0)}

    Box(modifier = modifier, contentAlignment = Alignment.Center){
        Surface(
            modifier = Modifier
                .size(size.width, size.height / 3),
            shape = selectorShape,
            color = selectorColor,
            border = selectorBorder
        ) {}
        Row {
            WheelTextPicker(
                size = DpSize(size.width / 3, size.height),
                texts = dayTexts.value,
                selectedIndex = localDateNow.dayOfMonth - 1,
                onScrollFinished = { selectedIndex ->
                    selectedDayOfMonth.value = dayTexts.value[selectedIndex].toInt()
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
                        onScrollFinished(null)
                    }
                },
                selectorEnabled = false
            )
            WheelTextPicker(
                size = DpSize(size.width / 3, size.height),
                texts = monthTexts,
                selectedIndex = localDateNow.month.value - 1,
                onScrollFinished = { selectedIndex ->
                    selectedMonth.value = selectedIndex + 1
                    dayTexts.value = calculateDayTexts(selectedMonth.value, selectedYear.value)
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
                        onScrollFinished(null)
                    }
                },
                selectorEnabled = false
            )
            WheelTextPicker(
                size = DpSize(size.width / 3, size.height),
                texts = yearTexts,
                selectedIndex = yearRange,
                onScrollFinished = { selectedIndex ->
                    selectedYear.value = yearTexts[selectedIndex].toInt()
                    dayTexts.value = calculateDayTexts(selectedMonth.value, selectedYear.value)
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
                        onScrollFinished(null)
                    }
                },
                selectorEnabled = false
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun calculateDayTexts(month: Int, year: Int): List<String> {

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