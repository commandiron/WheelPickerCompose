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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WheelTimePicker(
    modifier: Modifier = Modifier,
    currentTime: LocalTime = LocalTime.now(),
    disableBackwards: Boolean = false,
    size: DpSize = DpSize(128.dp, 128.dp),
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textColor: Color = LocalContentColor.current,
    infiniteLoopEnabled: Boolean = false,
    selectorEnabled: Boolean = true,
    selectorShape: Shape = RoundedCornerShape(16.dp),
    selectorColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
    selectorBorder: BorderStroke? = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
    onScrollFinished : (snappedTime: LocalTime) -> Unit = {}
) {
    val hourTexts: List<String> = listOf(
        "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
        "15", "16", "17", "18", "19", "20", "21", "22", "23"
    )
    val selectedHour = remember { mutableStateOf(currentTime.hour) }

    val minuteTexts: List<String> = listOf(
        "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
        "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
        "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44",
        "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59",
    )
    val selectedMinute = remember { mutableStateOf(0) }

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
                size = DpSize(size.width / 2, size.height),
                texts = hourTexts,
                textStyle = textStyle,
                textColor = textColor,
                infiniteLoopEnabled = infiniteLoopEnabled,
                startIndex = currentTime.hour,
                selectorEnabled = false,
                onScrollFinished = { selectedIndex ->
                    selectedHour.value =
                        if(disableBackwards){
                            if(selectedIndex < currentTime.hour ) {
                                currentTime.hour
                            } else selectedIndex
                        }else{
                            selectedIndex
                        }
                    try {
                        onScrollFinished(
                            LocalTime.of(
                                selectedHour.value,
                                selectedMinute.value
                            )
                        )
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                    if(disableBackwards){
                        if(selectedIndex < currentTime.hour ) {
                            return@WheelTextPicker currentTime.hour
                        }else{
                            return@WheelTextPicker selectedIndex
                        }
                    }else{
                        return@WheelTextPicker selectedIndex
                    }
                }
            )
            WheelTextPicker(
                size = DpSize(size.width / 2, size.height),
                texts = minuteTexts,
                textStyle = textStyle,
                textColor = textColor,
                infiniteLoopEnabled = infiniteLoopEnabled,
                startIndex = currentTime.minute,
                selectorEnabled = false,
                onScrollFinished = { selectedIndex ->
                    selectedMinute.value =
                        if(disableBackwards){
                            if(selectedIndex < currentTime.minute ) {
                                currentTime.minute
                            } else selectedIndex
                        }else{
                            selectedIndex
                        }
                    try {
                        onScrollFinished(
                            LocalTime.of(
                                selectedHour.value,
                                selectedMinute.value
                            )
                        )
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                    if(disableBackwards){
                        if(selectedIndex < currentTime.minute ) {
                            return@WheelTextPicker currentTime.minute
                        }else{
                            return@WheelTextPicker selectedIndex
                        }
                    }else{
                        return@WheelTextPicker selectedIndex
                    }
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











