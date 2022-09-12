package com.commandiron.wheel_picker_compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import dev.chrisbanes.snapper.*

@Composable
fun WheelPicker(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
    size: DpSize = DpSize(128.dp, 128.dp),
    startIndex: Int = 0,
    count: Int,
    selectorEnabled: Boolean = false,
    selectorShape: Shape = RoundedCornerShape(0.dp),
    selectorColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
    selectorBorder: BorderStroke? = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
    onScrollFinished: (snappedIndex: Int) -> Int? = {null},
    content: @Composable BoxScope.(index: Int, snappedIndex: Int) -> Unit,
) {
    val layoutInfo = rememberLazyListSnapperLayoutInfo(lazyListState)
    val snappedIndex = remember { mutableStateOf(startIndex)}
    LaunchedEffect(lazyListState.isScrollInProgress, count) {
        if (!lazyListState.isScrollInProgress) {
            val snappedItem = layoutInfo.currentItem
            snappedItem?.let {  snapperLayoutItemInfo ->
                if(snapperLayoutItemInfo.offset == 0){
                    snappedIndex.value = snapperLayoutItemInfo.index
                }else{
                    snappedIndex.value = snapperLayoutItemInfo.index + 1
                }
                onScrollFinished(if(snappedIndex.value < count) snappedIndex.value else count - 1)?.let {
                    lazyListState.scrollToItem(it)
                    snappedIndex.value = it
                }
            }
        }
    }
    LaunchedEffect(key1 = startIndex){
        lazyListState.scrollToItem(startIndex)
        snappedIndex.value = startIndex
        onScrollFinished(if(snappedIndex.value < count) snappedIndex.value else count - 1)
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
                .width(size.width),
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
                    content(index, snappedIndex.value)
                }
            }
        }
    }
}