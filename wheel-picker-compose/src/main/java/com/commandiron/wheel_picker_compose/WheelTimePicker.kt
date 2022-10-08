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
    startTime: LocalTime = LocalTime.now(),
    disablePastTime: Boolean = false,
    size: DpSize = DpSize(128.dp, 128.dp),
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textColor: Color = LocalContentColor.current,
    selectorEnabled: Boolean = true,
    selectorShape: Shape = RoundedCornerShape(16.dp),
    selectorColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
    selectorBorder: BorderStroke? = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
    onScrollFinished : (snappedTime: LocalTime) -> Unit = {}
) {
    val hourTexts: List<String> = (0..23).map { it.toString().padStart(2, '0') }
    val selectedHour = remember { mutableStateOf(startTime.hour) }

    val minuteTexts: List<String> = (0..59).map { it.toString().padStart(2, '0') }
    val selectedMinute = remember { mutableStateOf(startTime.minute) }

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
                startIndex = startTime.hour,
                selectorEnabled = false,
                onScrollFinished = { selectedIndex ->
                    try {
                        val selectedTime = LocalTime.of(selectedIndex, selectedMinute.value)
                        val isTimeBefore = isTimeBefore(selectedTime, startTime)

                        if(disablePastTime){
                            if(!isTimeBefore){
                                selectedHour.value = selectedIndex
                            }
                        }else{
                            selectedHour.value = selectedIndex
                        }

                        onScrollFinished(
                            LocalTime.of(
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
                size = DpSize(size.width / 2, size.height),
                texts = minuteTexts,
                textStyle = textStyle,
                textColor = textColor,
                startIndex = startTime.minute,
                selectorEnabled = false,
                onScrollFinished = { selectedIndex ->
                    try {
                        val selectedTime = LocalTime.of(selectedHour.value, selectedIndex)
                        val isTimeBefore = isTimeBefore(selectedTime, startTime)

                        if(disablePastTime){
                            if(!isTimeBefore){
                                selectedMinute.value = selectedIndex
                            }
                        }else{
                            selectedMinute.value = selectedIndex
                        }

                        onScrollFinished(
                            LocalTime.of(
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

@RequiresApi(Build.VERSION_CODES.O)
private fun isTimeBefore(time: LocalTime, currentTime: LocalTime): Boolean{
    return time.isBefore(currentTime)
}











