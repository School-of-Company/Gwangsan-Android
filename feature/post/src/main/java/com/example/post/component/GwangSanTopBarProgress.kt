package com.school_of_company.design_system.component.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
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
    progressColor: Color = GwangSanColor.subBlue300
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(8.dp)
            .background(backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progressRatio.coerceIn(0f, 1f))
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
