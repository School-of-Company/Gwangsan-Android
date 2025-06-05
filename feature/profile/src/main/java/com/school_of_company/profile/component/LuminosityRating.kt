package com.school_of_company.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.theme.GwangSanTheme

@Composable
internal fun BrightnessProgressBar(
    modifier: Modifier = Modifier,
    brightnessLevel: Int,
    maxLevel: Int = 10
) {
    GwangSanTheme { colors, typography ->

        val progress = brightnessLevel.toFloat() / maxLevel
        val progressColor = when (brightnessLevel) {
            in 1..3 -> colors.subYellow700
            in 4..6 -> colors.subYellow500
            in 7..10 -> colors.subYellow300
            else -> colors.gray200
        }

        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(colors.white)
        ) {
            Text(
                text = "밝기",
                style = typography.body1
            )

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(RoundedCornerShape(50))
                    .background(colors.gray200)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress)
                        .fillMaxHeight()
                        .padding(2.dp)
                        .clip(RoundedCornerShape(50))
                        .background(progressColor)
                )
            }

            Text(
                text = "${brightnessLevel}단계",
                color = progressColor,
                style = typography.body2,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Preview
@Composable
fun PreviewBrightnessProgressBar(
){
    BrightnessProgressBar(
        brightnessLevel = 7,
        maxLevel = 10
    )
}