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

    val dayTexts = listOf(
        "1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17",
        "18","19","20","21","22","23","24","25","26","27","28","29","30","31"
    )
    val selectedDayOfMonth = remember { mutableStateOf(0)}

    val monthTexts = listOf(
        "January", "February", "March", "April", "May", "June", "July", "August",
        "September", "October", "November", "December"
    )
    val selectedMonth = remember { mutableStateOf(0)}

    val yearRange = 100
    var yearTexts = listOf<String>()
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
                texts = dayTexts,
                selectedIndex = localDateNow.dayOfMonth - 1,
                onScrollFinished = { selectedIndex ->
                    selectedDayOfMonth.value = dayTexts[selectedIndex].toInt()
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