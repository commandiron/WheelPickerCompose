package com.commandiron.wheelpickercompose

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.commandiron.wheel_picker_compose.*
import com.commandiron.wheelpickercompose.ui.theme.WheelPickerComposeTheme
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
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
                        WheelDateTimePicker { snappedDateTime -> }
                        WheelDatePicker { snappedDate -> }
                        WheelTimePicker { snappedTime -> }
                        WheelTextPicker(texts = (1..6).map { "Text $it" })
                        WheelPicker(count = 6) { index, snappedIndex ->
                            Card(Modifier.size(128.dp).padding(8.dp)) {}
                        }
//                        WheelDateTimePicker(
//                            currentDateTime = LocalDateTime.of(
//                                2025, 10, 30, 5, 0
//                            ),
//                            disablePastDateTime = true,
//                            size = DpSize(200.dp, 100.dp),
//                            textStyle = MaterialTheme.typography.titleSmall,
//                            textColor = Color(0xFFffc300),
//                            selectorEnabled = true,
//                            selectorShape = RoundedCornerShape(0.dp),
//                            selectorColor = Color(0xFFf1faee).copy(alpha = 0.2f),
//                            selectorBorder = BorderStroke(2.dp, Color(0xFFf1faee))
//                        ) { snappedDateTime -> }
                    }
                }
            }
        }
    }
}