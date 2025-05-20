package com.school_of_company.design_system.componet.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.school_of_company.design_system.componet.clickable.GwanGsanClickable
import com.school_of_company.design_system.componet.dropdown.state.GwangSanSwitchState
import com.school_of_company.design_system.theme.color.GwangSanColor
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun GwangSanSwitchButton(
    height: Dp = 45.dp,
    width: Dp = 345.dp,
    switchOnBackground: Color = Color(0xFFEAC96D),
    switchOffBackground: Color = Color(0xFFEAC96D),
    stateOn: GwangSanSwitchState,
    stateOff: GwangSanSwitchState,
    initialValue: GwangSanSwitchState,
    onCheckedChanged: (GwangSanSwitchState) -> Unit
) {
    var clickListener by remember { mutableStateOf(false) }

    val sizePx = with(LocalDensity.current) { (width / 2).toPx() }
    val anchors = mapOf(0f to stateOff, sizePx to stateOn)

    val swipeableState = rememberSwipeableState(initialValue = initialValue)
    val scope = rememberCoroutineScope()

    LaunchedEffect(swipeableState.currentValue) {
        onCheckedChanged(swipeableState.currentValue)
    }

    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(50))
            .background(
                if (swipeableState.currentValue == stateOff) {
                    switchOffBackground
                } else {
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
        // 흰색 스위치 동그라미
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .offset {
                        IntOffset(swipeableState.offset.value.roundToInt(), 0)
                    }
                    .width(width / 2)
                    .padding(horizontal = 8.dp, vertical = 7.dp)
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

        // 텍스트
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
                    color = if (swipeableState.currentValue == GwangSanSwitchState.REQUEST) Color.Black else Color.Gray
                )
            }
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "필요해요",
                    color = if (swipeableState.currentValue == GwangSanSwitchState.NEED) Color.Black else Color.Gray
                )
            }
        }
    }
}

@Preview
@Composable
fun GwangSanSwitchButtonPreview() {
    GwangSanSwitchButton(
        stateOn = GwangSanSwitchState.NEED,
        stateOff = GwangSanSwitchState.REQUEST,
        initialValue = GwangSanSwitchState.REQUEST,
        switchOffBackground = GwangSanColor.subYellow500,
        switchOnBackground = GwangSanColor.gray500,
    ) {
        when (it) {
            GwangSanSwitchState.REQUEST -> println("현재 상태: 해주세요")
            GwangSanSwitchState.NEED -> println("현재 상태: 필요해요")
        }
    }
}