package com.commandiron.wheel_picker_compose

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

@Composable
fun WheelTextPicker(
    modifier: Modifier = Modifier,
    size: DpSize = DpSize(128.dp, 128.dp),
    selectedIndex: Int = 0,
    texts: List<String>,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textColor: Color = LocalContentColor.current,
    selectorEnabled: Boolean = true,
    selectorShape: Shape = RoundedCornerShape(0.dp),
    selectorColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
    onScrollFinished : (snappedIndex: Int) -> Unit
) {
    WheelPicker(
        modifier = modifier,
        size = size,
        selectedIndex = selectedIndex,
        count = texts.size,
        selectorEnabled = selectorEnabled,
        selectorShape = selectorShape,
        selectorColor = selectorColor,
        onScrollFinished = onScrollFinished
    ){ index, snappedIndex ->
        Text(
            text = texts[index],
            style = textStyle,
            color = textColor.copy(
                alpha = when(snappedIndex){
                    index + 1 -> {
                        0.2f
                    }
                    index -> {
                        1f
                    }
                    index - 1 -> {
                        0.2f
                    }
                    else -> 0.2f
                }
            )
        )
    }
}