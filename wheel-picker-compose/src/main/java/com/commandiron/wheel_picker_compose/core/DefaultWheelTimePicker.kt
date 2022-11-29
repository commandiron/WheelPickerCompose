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

    var snappedTime by remember { mutableStateOf(startTime) }

    val hours = (0..23).map {
        Hour(
            text = it.toString().padStart(2, '0'),
            value = it,
            index = it
        )
    }

    val minutes = (0..59).map {
        Minute(
            text = it.toString().padStart(2, '0'),
            value = it,
            index = it
        )
    }

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
                startIndex = hours.find { it.value == startTime.hour }?.index ?: 0,
                selectorProperties = WheelPickerDefaults.selectorProperties(
                    enabled = false
                ),
                onScrollFinished = { snappedIndex ->

                    val newHour = hours.find { it.index == snappedIndex }?.value

                    newHour?.let {

                        val newTime = snappedTime.withHour(newHour)

                        val isTimeBefore = isTimeBefore(newTime, startTime)

                        if (backwardsDisabled) {
                            if (!isTimeBefore) {
                                snappedTime = newTime
                            }
                        } else {
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

                    return@WheelTextPicker hours.find { it.value == snappedTime.hour }?.index
                }
            )
            //Minute
            WheelTextPicker(
                size = DpSize(size.width / 2, size.height),
                texts = minutes.map { it.text },
                style = textStyle,
                color = textColor,
                startIndex = minutes.find { it.value == startTime.minute }?.index ?: 0,
                selectorProperties = WheelPickerDefaults.selectorProperties(
                    enabled = false
                ),
                onScrollFinished = { snappedIndex ->

                    val newMinute = minutes.find { it.index == snappedIndex }?.value

                    newMinute?.let {

                        val newTime = snappedTime.withMinute(newMinute)

                        val isTimeBefore = isTimeBefore(newTime, startTime)

                        if(backwardsDisabled){
                            if(!isTimeBefore){
                                snappedTime = newTime
                            }
                        }else{
                            snappedTime = newTime
                        }

                        val newIndex = minutes.find { it.value == snappedTime.minute }?.index

                        newIndex?.let {
                            onSnappedTime(
                                SnappedTime.Minute(
                                    localTime = snappedTime,
                                    index = newIndex
                                )
                            )?.let { return@WheelTextPicker it }
                        }
                    }

                    return@WheelTextPicker minutes.find { it.value == snappedTime.minute }?.index
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

data class Minute(
    val text: String,
    val value: Int,
    val index: Int
)











