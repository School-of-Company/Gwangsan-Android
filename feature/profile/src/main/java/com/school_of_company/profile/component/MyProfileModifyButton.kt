package com.school_of_company.profile.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.school_of_company.design_system.R
import com.school_of_company.design_system.component.clickable.GwangSanClickable

@Composable
internal fun MyProfileModifyButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
            modifier = modifier
                .clip(RoundedCornerShape(30.dp))
                .border(
                    width = 1.dp,
                    color = Color(0xFF8FC31D),
                    shape = RoundedCornerShape(size = 30.dp)
                )
                .GwangSanClickable { onClick() }
                .padding(
                    horizontal = 16.dp,
                    vertical = 10.dp
                )
    ) {
        Text(
            text = "내 정보 수정",
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 16.8.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_light)),
                fontWeight = FontWeight(400),
                color = Color(0xFF8FC31D),
                )
        )
    }
}

@Preview
@Composable
private fun MyProfileModifyButtonPreview() {
    MyProfileModifyButton(
        onClick = {}
    )
}