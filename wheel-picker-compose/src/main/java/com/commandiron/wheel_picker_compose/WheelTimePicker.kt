package com.commandiron.wheel_picker_compose

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.commandiron.wheel_picker_compose.core.SelectorProperties
import com.commandiron.wheel_picker_compose.core.WheelPickerDefaults
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WheelTimePicker(
    modifier: Modifier = Modifier,
    startTime: LocalTime = LocalTime.now(),
    backwardsDisabled: Boolean = false,
    size: DpSize = DpSize(128.dp, 128.dp),
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textColor: Color = LocalContentColor.current,
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
    onSnappedTimeIndex : (snappedTimeIndex: Int) -> Int? = { null },
    onSnappedTime: (snappedTime: LocalTime) -> Unit
) {
    val hourTexts: List<String> = (0..23).map { it.toString().padStart(2, '0') }
    val minuteTexts: List<String> = (0..59).map { it.toString().padStart(2, '0') }

    var snappedTime by remember { mutableStateOf(startTime) }

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
            //Hour
            WheelTextPicker(
                size = DpSize(size.width / 2, size.height),
                texts = hourTexts,
                style = textStyle,
                color = textColor,
                startIndex = startTime.hour,
                selectorProperties = WheelPickerDefaults.selectorProperties(
                    enabled = false
                ),
                onScrollFinished = { snappedIndex ->
                    try {

                        val isTimeBefore = isTimeBefore(snappedTime.withHour(snappedIndex), startTime)

                        if(backwardsDisabled){
                            if(!isTimeBefore){
                                snappedTime = snappedTime.withHour(snappedIndex)
                            }
                        }else{
                            snappedTime = snappedTime.withHour(snappedIndex)
                        }

                        onSnappedTimeIndex(snappedIndex)?.let { return@WheelTextPicker it }
                        onSnappedTime(snappedTime)

                    }catch (e: Exception){
                        e.printStackTrace()
                    }

                    return@WheelTextPicker snappedTime.hour
                }
            )
            //Minute
            WheelTextPicker(
                size = DpSize(size.width / 2, size.height),
                texts = minuteTexts,
                style = textStyle,
                color = textColor,
                startIndex = startTime.minute,
                selectorProperties = WheelPickerDefaults.selectorProperties(
                    enabled = false
                ),
                onScrollFinished = { snappedIndex ->
                    try {

                        val isTimeBefore = isTimeBefore(snappedTime.withMinute(snappedIndex), startTime)

                        if(backwardsDisabled){
                            if(!isTimeBefore){
                                snappedTime = snappedTime.withMinute(snappedIndex)
                            }
                        }else{
                            snappedTime = snappedTime.withMinute(snappedIndex)
                        }

                        onSnappedTimeIndex(snappedIndex)?.let { return@WheelTextPicker it }
                        onSnappedTime(snappedTime)

                    }catch (e: Exception){
                        e.printStackTrace()
                    }

                    return@WheelTextPicker snappedTime.minute
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











