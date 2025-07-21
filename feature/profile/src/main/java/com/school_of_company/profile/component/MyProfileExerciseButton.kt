package com.school_of_company.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.school_of_company.design_system.R
import com.school_of_company.design_system.component.clickable.GwangSanClickable
import com.school_of_company.design_system.theme.GwangSanTheme

@Composable
internal fun MyProfileExerciseButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    onClick: () -> Unit
) {
    GwangSanTheme { colors, typography ->

        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .clip(RoundedCornerShape(8.dp))
                .background(color = colors.white)
                .border(
                    width = 1.dp,
                    color = Color(0xFF8FC31D),
                    shape = RoundedCornerShape(size = 8.dp)
                )
                .GwangSanClickable { onClick() }
                .padding(vertical = 20.dp)
        ) {
            Text(
                text = buttonText,
                style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 26.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF8FC31D),
                    textAlign = TextAlign.Center,
                )
            )
        }
    }
}

@Composable
@Preview
fun ExerciseButtonsExample() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MyProfileExerciseButton(
            modifier = Modifier.weight(1f), // 균등 분배
            buttonText = "내 글",
            onClick = { /* 액션 */ }
        )
        MyProfileExerciseButton(
            modifier = Modifier.weight(1f), // 균등 분배
            buttonText = "거래내역",
            onClick = { /* 액션 */ }
        )
        MyProfileExerciseButton(
            modifier = Modifier.weight(1f), // 균등 분배
            buttonText = "내가 작성한 후기",
            onClick = { /* 액션 */ }
        )
    }
}