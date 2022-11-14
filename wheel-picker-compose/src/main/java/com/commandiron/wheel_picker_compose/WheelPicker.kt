package com.commandiron.wheel_picker_compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import dev.chrisbanes.snapper.SnapperLayoutInfo
import dev.chrisbanes.snapper.rememberLazyListSnapperLayoutInfo
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior

@Composable
fun WheelPicker(
    modifier: Modifier = Modifier,
    size: DpSize = DpSize(128.dp, 128.dp),
    startIndex: Int = 0,
    count: Int,
    selectorEnabled: Boolean = false,
    selectorShape: Shape = RoundedCornerShape(0.dp),
    selectorColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
    selectorBorder: BorderStroke? = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
    onScrollFinished: (snappedIndex: Int) -> Int? = { null },
    content: @Composable BoxScope.(index: Int) -> Unit,
) {
    val lazyListState = rememberLazyListState(startIndex)
    val snapperLayoutInfo = rememberLazyListSnapperLayoutInfo(lazyListState = lazyListState)
    val isScrollInProgress = lazyListState.isScrollInProgress

    LaunchedEffect(isScrollInProgress) {
        if(!isScrollInProgress) {
            onScrollFinished(calculateSelectedItem(snapperLayoutInfo)?: startIndex)
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        if(selectorEnabled){
            Surface(
                modifier = Modifier
                    .size(size.width, size.height / 3),
                shape = selectorShape,
                color = selectorColor,
                border = selectorBorder
            ) {}
        }
        LazyColumn(
            modifier = Modifier
                .height(size.height)
                .width(size.width).border(1.dp, Color.Red),
            state = lazyListState,
            contentPadding = PaddingValues(vertical = size.height / 3),
            flingBehavior = rememberSnapperFlingBehavior(
                lazyListState = lazyListState
            )
        ){
            items(count){ index ->
                Box(
                    modifier = Modifier
                        .height(size.height / 3)
                        .width(size.width),
                    contentAlignment = Alignment.Center
                ) {
                    content(index)
                }
            }
        }
    }
}

private fun calculateSelectedItem(snapperLayoutInfo: SnapperLayoutInfo): Int? {
    val currentItemIndex = snapperLayoutInfo.currentItem?.index
    return if(currentItemIndex != null) {
        if(snapperLayoutInfo.currentItem?.offset != 0) currentItemIndex + 1 else currentItemIndex
    } else null
}