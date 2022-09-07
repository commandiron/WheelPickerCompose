package com.commandiron.wheel_picker_compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import dev.chrisbanes.snapper.*

@OptIn(ExperimentalSnapperApi::class)
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
    val lazyListState = rememberLazyListState()
    LaunchedEffect(key1 = Unit){
        lazyListState.scrollToItem(selectedIndex)
    }

    val layoutInfo = rememberLazyListSnapperLayoutInfo(lazyListState)
    val snappedIndex = remember { mutableStateOf(selectedIndex)}
    LaunchedEffect(lazyListState.isScrollInProgress) {
        if (!lazyListState.isScrollInProgress) {
            val snappedItem = layoutInfo.currentItem
            snappedItem?.let {  snapperLayoutItemInfo ->
                if(snapperLayoutItemInfo.offset == 0){
                    snappedIndex.value = snapperLayoutItemInfo.index
                }else{
                    snappedIndex.value = snapperLayoutItemInfo.index + 1
                }
                onScrollFinished(snappedIndex.value)
            }
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
                color = selectorColor
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
            items(texts.size){ index ->
                BoxWithConstraints(
                    modifier = Modifier
                        .height(size.height / 3)
                        .width(size.width),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = texts[index],
                        style = textStyle,
                        color = textColor.copy(
                            alpha = when(snappedIndex.value){
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
        }
    }
}