package com.commandiron.wheel_picker_compose.core

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
    startIndex: Int = 0,
    count: Int,
    size: DpSize = DpSize(128.dp, 128.dp),
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
    onScrollFinished: (snappedIndex: Int) -> Int? = { null },
    content: @Composable LazyItemScope.(index: Int) -> Unit,
) {
    val lazyListState = rememberLazyListState(startIndex)
    val snapperLayoutInfo = rememberLazyListSnapperLayoutInfo(lazyListState = lazyListState)
    val isScrollInProgress = lazyListState.isScrollInProgress

    LaunchedEffect(isScrollInProgress, count) {
        if(!isScrollInProgress) {
            onScrollFinished(calculateSnappedItemIndex(snapperLayoutInfo) ?: startIndex)?.let {
                lazyListState.scrollToItem(it)
            }
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        if(selectorProperties.enabled().value){
            Surface(
                modifier = Modifier
                    .size(size.width, size.height / 3),
                shape = selectorProperties.shape().value,
                color = selectorProperties.color().value,
                border = selectorProperties.border().value
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
                        .width(size.width)
                        .alpha(
                            calculateAnimatedAlpha(
                                lazyListState = lazyListState,
                                snappedItemIndex = calculateSnappedItemIndex(snapperLayoutInfo) ?: index,
                                index = index
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    content(index)
                }
            }
        }
    }
}

private fun calculateSnappedItemIndex(snapperLayoutInfo: SnapperLayoutInfo): Int? {
    var currentItemIndex = snapperLayoutInfo.currentItem?.index
    if(snapperLayoutInfo.currentItem?.offset != 0) {
        if(currentItemIndex != null) {
            currentItemIndex ++
        }
    }
    return currentItemIndex
}

@Composable
private fun calculateAnimatedAlpha(lazyListState: LazyListState, snappedItemIndex: Int, index: Int): Float {

    val firstVisibleItemScrollOffset = remember { derivedStateOf { lazyListState.firstVisibleItemScrollOffset } }.value
    val lastIndex = remember { derivedStateOf { lazyListState.layoutInfo } }.value.totalItemsCount - 1

    var resultAlpha = 0.2f

    if(!lazyListState.isScrollInProgress) {
        if(snappedItemIndex == index) {
            resultAlpha = if(lastIndex == index) 1f else if(firstVisibleItemScrollOffset == 0) 1f else 0.2f
        }
    }

    return animateFloatAsState(
        targetValue = resultAlpha
    ).value
}

object WheelPickerDefaults{
    @Composable
    fun selectorProperties(
        enabled: Boolean = true,
        shape: Shape = RoundedCornerShape(16.dp),
        color: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
        border: BorderStroke? = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
    ): SelectorProperties = DefaultSelectorProperties(
        enabled = enabled,
        shape = shape,
        color = color,
        border = border
    )
}

interface SelectorProperties {
    @Composable
    fun enabled(): State<Boolean>
    @Composable
    fun shape(): State<Shape>
    @Composable
    fun color(): State<Color>
    @Composable
    fun border(): State<BorderStroke?>
}

@Immutable
internal class DefaultSelectorProperties(
    private val enabled: Boolean,
    private val shape: Shape,
    private val color: Color,
    private val border: BorderStroke?
) : SelectorProperties {

    @Composable
    override fun enabled(): State<Boolean> {
        return rememberUpdatedState(enabled)
    }

    @Composable
    override fun shape(): State<Shape> {
        return rememberUpdatedState(shape)
    }

    @Composable
    override fun color(): State<Color> {
        return rememberUpdatedState(color)
    }

    @Composable
    override fun border(): State<BorderStroke?> {
        return rememberUpdatedState(border)
    }
}


