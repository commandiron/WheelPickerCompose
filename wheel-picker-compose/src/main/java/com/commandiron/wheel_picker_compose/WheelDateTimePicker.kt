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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WheelDateTimePicker(
    modifier: Modifier = Modifier,
    currentDateTime: LocalDateTime = LocalDateTime.now(),
    disableDateBackwards: Boolean = false,
    disableTimeBackwards: Boolean = false,
    size: DpSize = DpSize(256.dp, 128.dp),
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textColor: Color = LocalContentColor.current,
    infiniteLoopEnabled: Boolean = false,
    selectorEnabled: Boolean = true,
    selectorShape: Shape = RoundedCornerShape(16.dp),
    selectorColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
    selectorBorder: BorderStroke? = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
    onScrollFinished : (snappedDate: LocalDate, snappedTime: LocalTime) -> Unit = { _, _ -> }
) {
    val currentDate = remember { mutableStateOf<LocalDate>(currentDateTime.toLocalDate()) }
    val currentTime = remember { mutableStateOf<LocalTime>(currentDateTime.toLocalTime()) }
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
            WheelDatePicker(
                currentDate = currentDateTime.toLocalDate(),
                disableBackwards = disableDateBackwards,
                size = DpSize(size.width * 2 / 3, size.height),
                textStyle = textStyle,
                textColor = textColor,
                infiniteLoopEnabled = infiniteLoopEnabled,
                selectorEnabled = false,
                onScrollFinished = {
                    currentDate.value = it
                    onScrollFinished(currentDate.value, currentTime.value)
                }
            )
            WheelTimePicker(
                currentTime = currentDateTime.toLocalTime(),
                disableBackwards = disableTimeBackwards,
                size = DpSize(size.width / 3, size.height),
                textStyle = textStyle,
                textColor = textColor,
                infiniteLoopEnabled = infiniteLoopEnabled,
                selectorEnabled = false,
                onScrollFinished = {
                    currentTime.value = it
                    onScrollFinished(currentDate.value, currentTime.value)
                }
            )
        }
    }
}












