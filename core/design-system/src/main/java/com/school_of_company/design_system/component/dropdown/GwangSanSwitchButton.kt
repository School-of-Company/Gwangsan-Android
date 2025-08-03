package com.school_of_company.design_system.component.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.school_of_company.design_system.component.clickable.GwangSanClickable
import com.school_of_company.design_system.component.dropdown.state.GwangSanSwitchState
import com.school_of_company.design_system.theme.color.GwangSanColor
import com.school_of_company.model.enum.Type
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun GwangSanSwitchButton(
    modifier: Modifier = Modifier,
    height: Dp = 45.dp,
    stateOn: GwangSanSwitchState,
    stateOff: GwangSanSwitchState,
    type: Type,
    initialValue: GwangSanSwitchState,
    onCheckedChanged: (GwangSanSwitchState) -> Unit
) {
    var clickListener by remember { mutableStateOf(false) }

    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val containerWidth = maxWidth
        val sizePx = with(LocalDensity.current) { (containerWidth / 2).toPx() }
        val anchors = mapOf(0f to stateOff, sizePx to stateOn)

        val swipeableState = rememberSwipeableState(initialValue = initialValue)
        val scope = rememberCoroutineScope()

        LaunchedEffect(swipeableState.currentValue) {
            onCheckedChanged(swipeableState.currentValue)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .clip(RoundedCornerShape(50))
                .background(color = GwangSanColor.subYellow300)
                .GwangSanClickable(interval = 50L) {
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
            Box(
                modifier = Modifier
                    .offset {
                        IntOffset(swipeableState.offset.value.roundToInt(), 0)
                    }
                    .width(containerWidth / 2)
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
                        text = if (type == Type.SERVICE) "해주세요" else "필요해요",
                        color = if (swipeableState.currentValue == GwangSanSwitchState.REQUEST) Color.Black else Color.Gray
                    )
                }
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (type == Type.SERVICE) "할수있어요" else "팔아요",
                        color = if (swipeableState.currentValue == GwangSanSwitchState.NEED) Color.Black else Color.Gray
                    )
                }
            }
        }
    }
}