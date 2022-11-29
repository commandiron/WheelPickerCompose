package com.commandiron.wheel_picker_compose.core

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
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
internal fun DefaultWheelTimePicker(
    modifier: Modifier = Modifier,
    startTime: LocalTime = LocalTime.now(),
    backwardsDisabled: Boolean = false,
    size: DpSize = DpSize(128.dp, 128.dp),
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textColor: Color = LocalContentColor.current,
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
    onSnappedTime : (snappedTime: SnappedTime) -> Int? = { _ -> null },
) {

    val hours = (0..23).map {
        Hour(
            text = it.toString().padStart(2, '0'),
            value = it,
            index = it
        )
    }

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
                texts = hours.map { it.text },
                style = textStyle,
                color = textColor,
                startIndex = startTime.hour,
                selectorProperties = WheelPickerDefaults.selectorProperties(
                    enabled = false
                ),
                onScrollFinished = { snappedIndex ->

                    val newHour = hours.find { it.index == snappedIndex }?.value

                    newHour?.let {

                        val newTime = snappedTime.withHour(newHour)

                        val isTimeBefore = isTimeBefore(newTime, startTime)

                        if(backwardsDisabled){
                            if(!isTimeBefore){
                                snappedTime = newTime
                            }
                        }else{
                            snappedTime = newTime
                        }

                        val newIndex = hours.find { it.value == snappedTime.hour }?.index

                        newIndex?.let {
                            onSnappedTime(
                                SnappedTime.Hour(
                                    localTime = snappedTime,
                                    index = newIndex
                                )
                            )?.let { return@WheelTextPicker it }
                        }
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

                        val newTime = snappedTime.withMinute(snappedIndex)
                        val isTimeBefore = isTimeBefore(newTime, startTime)

                        if(backwardsDisabled){
                            if(!isTimeBefore){
                                snappedTime = newTime
                            }
                        }else{
                            snappedTime = newTime
                        }

                        onSnappedTime(
                            SnappedTime.Minute(snappedTime, snappedTime.minute)
                        )?.let { return@WheelTextPicker it }

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

data class Hour(
    val text: String,
    val value: Int,
    val index: Int
)











