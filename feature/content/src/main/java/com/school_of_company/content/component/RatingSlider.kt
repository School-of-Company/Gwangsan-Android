package com.school_of_company.design_system.component.slider

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.theme.GwangSanTheme
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun RatingSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    trackHeight: Dp = 6.dp,
    thumbRadius: Dp = 10.dp
) {
    GwangSanTheme { colors, _ ->
        val trackHeightPx = with(LocalDensity.current) { trackHeight.toPx() }
        val thumbRadiusPx = with(LocalDensity.current) { thumbRadius.toPx() }

        var sliderWidth by remember { mutableStateOf(1f) }

        Box(
            modifier = modifier
                .height(thumbRadius * 2)
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        val percent = offset.x / sliderWidth
                        val newValue = 1f + (4f * percent).coerceIn(0f, 1f)
                        onValueChange(newValue)
                    }
                }
                .drawBehind {
                    sliderWidth = size.width

                    val fraction = ((value - 1f) / 4f).coerceIn(0f, 1f)
                    val activeWidth = size.width * fraction
                    val trackY = center.y - trackHeightPx / 2

                    // draw focus (inactive track after thumb)
                    drawRoundRect(
                        color = colors.focus,
                        topLeft = Offset(activeWidth, trackY),
                        size = Size(size.width - activeWidth, trackHeightPx),
                        cornerRadius = CornerRadius(trackHeightPx / 2)
                    )

                    // draw active track
                    drawRoundRect(
                        color = colors.subYellow500,
                        topLeft = Offset(0f, trackY),
                        size = Size(activeWidth, trackHeightPx),
                        cornerRadius = CornerRadius(trackHeightPx / 2)
                    )

                    // draw thumb (white fill, yellow border)
                    val thumbX = activeWidth
                    drawCircle(
                        color = Color.White,
                        radius = thumbRadiusPx,
                        center = Offset(thumbX, center.y)
                    )
                    drawCircle(
                        color = colors.subYellow500,
                        radius = thumbRadiusPx,
                        center = Offset(thumbX, center.y),
                        style = Stroke(width = 2.dp.toPx())
                    )
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRatingSlider() {
    val (rating, setRating) = remember { mutableStateOf(3f) }

    RatingSlider(
        value = rating,
        onValueChange = setRating,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
    )
}