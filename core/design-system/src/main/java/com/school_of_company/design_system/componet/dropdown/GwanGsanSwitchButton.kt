package com.school_of_company.design_system.componet.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.school_of_company.design_system.componet.clickable.GwanGsanClickable
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun GSwitchButton(
    height: Dp = 50.dp,
    width: Dp = 240.dp,
    switchOnBackground: Color = Color(0xFFEAC96D),
    switchOffBackground: Color = Color(0xFFEAC96D),
    stateOn: Int,
    stateOff: Int,
    initialValue: Int,
    onCheckedChanged: (checked: Boolean) -> Unit
) {
    var clickListener by remember { mutableStateOf(false) }
    var isActivation by remember { mutableStateOf(false) }

    val swipeableState = rememberSwipeableState(initialValue = initialValue, confirmStateChange = { true })

    val sizePx = with(LocalDensity.current) { (width / 2).toPx() }
    val anchors = mapOf(0f to stateOff, sizePx to stateOn)
    val scope = rememberCoroutineScope()

    LaunchedEffect("init") {
        if (initialValue == stateOn) {
            clickListener = true
            isActivation = true
        }
    }

    LaunchedEffect(isActivation) {
        if (isActivation) onCheckedChanged(true) else onCheckedChanged(false)
    }

    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(50))
            .background(
                if (swipeableState.currentValue == stateOff) {
                    isActivation = false
                    switchOffBackground
                } else {
                    isActivation = true
                    switchOnBackground
                }
            )
            .GwanGsanClickable(interval = 50L) {
                clickListener = !clickListener
                scope.launch {
                    if (clickListener) {
                        swipeableState.animateTo(stateOn)
                    } else {
                        swipeableState.animateTo(stateOff)
                    }
                }
            }
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "해주세요",
                    color = if (!clickListener) Color.Black else Color.Gray
                )
            }
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "할수있어요",
                    color = if (clickListener) Color.Black else Color.Gray
                )
            }
        }
        Box(
            modifier = Modifier
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                .width(width / 2)
                .height(height)
                .clip(RoundedCornerShape(50))
                .background(Color.White)
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(0.3f) },
                    orientation = Orientation.Horizontal
                )
        )
    }
}

