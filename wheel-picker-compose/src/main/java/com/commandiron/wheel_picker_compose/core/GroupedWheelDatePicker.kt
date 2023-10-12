package com.commandiron.wheel_picker_compose.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.stream.Collectors
import java.util.stream.IntStream


@Composable
internal fun GroupedWheelDatePicker(
    modifier: Modifier = Modifier,
    startDate: LocalDate = LocalDate.now(),
    minDate: LocalDate = LocalDate.MIN,
    maxDate: LocalDate = LocalDate.MAX,
    todayLabel: String? = null,
    format: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy"),
    size: DpSize = DpSize(256.dp, 128.dp),
    rowCount: Int = 3,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textColor: Color = LocalContentColor.current,
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
    onSnappedDate : (snappedDate: GroupedDate) -> Unit = {},
) {
    var snappedDate by remember { mutableStateOf(startDate) }

    val dates = getDatesRange(minDate, maxDate).mapIndexed { index, date ->
        Date(
            todayLabel?.let { if (date.isToday) todayLabel else null } ?: date.format(format),
            date,
            index
        )
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        if (selectorProperties.enabled().value) {
            Surface(
                modifier = Modifier.size(size.width, size.height / rowCount),
                shape = selectorProperties.shape().value,
                color = selectorProperties.color().value,
                border = selectorProperties.border().value
            ) {}
        }
        Row {
            WheelTextPicker(
                size = DpSize(
                    width = size.width,
                    height = size.height
                ),
                texts = dates.map { it.text },
                rowCount = rowCount,
                style = textStyle,
                color = textColor,
                selectorProperties = WheelPickerDefaults.selectorProperties(enabled = false),
                startIndex = dates.find { it.value == startDate }?.index ?: 0,
                onScrollFinished = { snappedIndex ->
                    dates
                        .find { it.index == snappedIndex }
                        ?.also {

                            it.value.apply {
                                if (!isBefore(minDate) && !isAfter(maxDate)) {
                                    snappedDate = this
                                }
                            }

                            dates.find { it.value == snappedDate }?.index?.also {
                                onSnappedDate(
                                    GroupedDate.Date(localDate = snappedDate)
                                )
                            }
                        }
                    return@WheelTextPicker dates.find { it.value == snappedDate }?.index
                }
            )
        }
    }
}

private data class Date(
    val text: String,
    val value: LocalDate,
    val index: Int
)

private fun getDatesRange(startDate: LocalDate, endDate: LocalDate)
    = IntStream.iterate(0) { i -> i + 1 }
        .limit( ChronoUnit.DAYS.between(startDate, endDate.plusDays(1)))
        .mapToObj { i -> startDate.plusDays(i.toLong()) }
        .collect(Collectors.toList())

private val LocalDate.isToday
    get() = isEqual(LocalDate.now(ZoneId.systemDefault()))