package com.commandiron.wheel_picker_compose.core

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

@Composable
fun WheelTextPicker(
    modifier: Modifier = Modifier,
    startIndex: Int = 0,
    size: DpSize = DpSize(128.dp, 128.dp),
    texts: List<String>,
    contentDescriptions: List<String> = emptyList(),
    rowCount: Int,
    style: TextStyle = MaterialTheme.typography.titleMedium,
    color: Color = LocalContentColor.current,
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
    onScrollFinished: (snappedIndex: Int) -> Int? = { null },
) {
    var snappedIndex by remember { mutableStateOf(startIndex) }
    WheelPicker(
        modifier = modifier,
        startIndex = startIndex,
        size = size,
        count = texts.size,
        rowCount = rowCount,
        selectorProperties = selectorProperties,
        onScrollFinished = {
            snappedIndex = it
            onScrollFinished(it)
        }
    ){ index ->
        Text(
            modifier = Modifier.semantics {
                selected = snappedIndex == index
                contentDescription = contentDescriptions.getOrElse(index) { texts[index] }
            },
            text = texts[index],
            style = style,
            color = color,
            maxLines = 1
        )
    }
}