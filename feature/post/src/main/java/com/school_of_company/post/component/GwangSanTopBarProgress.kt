package com.school_of_company.design_system.component.progress

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.theme.color.GwangSanColor

@Composable
internal fun GwangSanTopBarProgress(
    modifier: Modifier = Modifier,
    progressRatio: Float,
    backgroundColor: Color = GwangSanColor.gray200,
    progressColor: Color = GwangSanColor.subBlue300,
    animationDurationMillis: Int = 500
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progressRatio.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = animationDurationMillis),
        label = "progressAnimation"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(8.dp)
            .background(backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(animatedProgress)
                .background(progressColor)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GwangSanTopBarProgressPreview_30() {
    GwangSanTopBarProgress(progressRatio = 0.3f)
}

@Preview(showBackground = true)
@Composable
fun GwangSanTopBarProgressPreview_70() {
    GwangSanTopBarProgress(progressRatio = 0.7f)
}

@Preview(showBackground = true)
@Composable
fun GwangSanTopBarProgressPreview_100() {
    GwangSanTopBarProgress(progressRatio = 1.0f)
}
