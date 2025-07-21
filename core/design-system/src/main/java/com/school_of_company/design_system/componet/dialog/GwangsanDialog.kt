package com.school_of_company.design_system.componet.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.school_of_company.design_system.R
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.theme.GwangSanTheme

@Composable
fun GwangsanDialog(
    modifier: Modifier = Modifier,
    onLogout: () -> Unit,
    onDismiss: () -> Unit,
    titleText: String = "로그아웃",
    contentText: String = "정말 로그아웃 하시겠어요?",
    buttonText: String = "로그아웃"
) {
    GwangSanTheme { colors, typography ->

        Column(
            modifier = modifier
                .background(
                    color = colors.white,
                    shape = RoundedCornerShape(size = 12.dp)
                )
                .padding(all = 16.dp)
        ) {
            Text(
                text = titleText,
                style = TextStyle(
                    fontSize = 20.sp,
                    lineHeight = 34.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF111111),
                ),
                textAlign = TextAlign.Start,
            )

            Spacer(modifier = Modifier.padding(bottom = 12.dp))

            Text(
                text = contentText,
                style = typography.body5,
                color = colors.gray300,
                modifier = Modifier.padding(end = 100.dp)
            )

            Spacer(modifier = Modifier.padding(bottom = 30.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "취소",
                    style = typography.body5,
                    color = colors.subBlue500,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                        .GwangSanClickable { onDismiss() }
                )

                Text(
                    text = buttonText,
                    style = typography.body5,
                    color = colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                        .GwangSanClickable { onLogout() }
                )
            }
        }
    }
}