package com.commandiron.wheelpickercompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.commandiron.wheel_picker_compose.GroupedWheelDateTimePicker
import com.commandiron.wheel_picker_compose.WheelDatePicker
import com.commandiron.wheel_picker_compose.WheelDateTimePicker
import com.commandiron.wheel_picker_compose.WheelTimePicker
import com.commandiron.wheel_picker_compose.core.TimeFormat
import com.commandiron.wheel_picker_compose.core.WheelPickerDefaults
import com.commandiron.wheelpickercompose.ui.theme.WheelPickerComposeTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WheelPickerComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        WheelTimePicker { snappedTime ->
                            println(snappedTime)
                        }
                        WheelDatePicker { snappedDate ->
                            println(snappedDate)
                        }
                        WheelDateTimePicker { snappedDateTime ->
                            println(snappedDateTime)
                        }
                        WheelDateTimePicker(
                            startDateTime = LocalDateTime.of(
                                2025, 10, 20, 5, 30
                            ),
                            minDateTime = LocalDateTime.now(),
                            maxDateTime = LocalDateTime.of(
                                2025, 10, 20, 5, 30
                            ),
                            timeFormat = TimeFormat.AM_PM,
                            size = DpSize(200.dp, 100.dp),
                            rowCount = 5,
                            textStyle = MaterialTheme.typography.titleSmall,
                            textColor = Color(0xFFffc300),
                            selectorProperties = WheelPickerDefaults.selectorProperties(
                                enabled = true,
                                shape = RoundedCornerShape(0.dp),
                                color = Color(0xFFf1faee).copy(alpha = 0.2f),
                                border = BorderStroke(2.dp, Color(0xFFf1faee))
                            )
                        ){ snappedDateTime ->
                            println(snappedDateTime)
                        }

                        GroupedWheelDateTimePicker(
                            startDateTime = LocalDateTime.of(
                                2023, 6, 22, 5, 30
                            ),
                            minDateTime = LocalDateTime.of(
                                2023, 4, 20, 5, 30
                            ),
                            maxDateTime = LocalDateTime.of(
                                2023, 10, 20, 5, 30
                            ),
                            todayLabel = "Today",
                            dateFormat = DateTimeFormatter.ofPattern("EEE d MMM yy"),
                            timeFormat = TimeFormat.HOUR_24,
                            size = DpSize(300.dp, 200.dp),
                            rowCount = 5,
                            textStyle = MaterialTheme.typography.bodyLarge,
                            textColor = Color(0xFFFFFFFF),
                            selectorProperties = WheelPickerDefaults.selectorProperties(
                                enabled = true,
                                shape = RoundedCornerShape(5.dp),
                                color = Color(0xFF036AB3).copy(alpha = 0.2f),
                                border = BorderStroke(1.dp, Color(0xFF036AB3))
                            )
                        ) { println(it) }
                    }
                }
            }
        }
    }
}