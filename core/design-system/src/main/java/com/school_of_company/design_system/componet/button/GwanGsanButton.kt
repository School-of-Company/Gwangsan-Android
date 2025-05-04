package com.school_of_company.design_system.componet.button

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.componet.button.state.ButtonState
import com.school_of_company.design_system.componet.clickable.GwanGsanClickable
import com.school_of_company.design_system.theme.GwangSanTheme

@Composable
fun GwangSanButton(
    modifier: Modifier = Modifier,
    text: String,
    color: Color,
    onClick: () -> Unit,
) {
    GwangSanTheme { colors, typography ->

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(
                    color = color,
                    shape = RoundedCornerShape(8.dp),
                )
                .GwanGsanClickable(
                    onClick = onClick,
                    rippleColor = colors.white
                )
                .then(modifier)
        ) {
            Text(
                text = text,
                style = typography.body1,
                fontWeight = FontWeight.SemiBold,
                color = colors.white
            )
        }
    }
}
// 상태에 따라 활성화/비활성화 가능한 버튼 컴포저블
@Composable
fun GwangSanStateButton(
    modifier: Modifier = Modifier,
    text: String,
    state: ButtonState = ButtonState.Enable, // 버튼의 상태 (기본값: Enable)
    onClick: () -> Unit,
) {
    GwangSanTheme { colors, typography ->

        val interactionSource = remember { MutableInteractionSource() } // 버튼의 상호작용 상태 저장

        // 버튼의 상태에 따라 활성화 여부를 결정하는 람다
        val enabledState: (buttonState: ButtonState) -> Boolean = {
            when (it) {
                ButtonState.Enable -> true  // Enable 상태면 활성화
                ButtonState.Disable -> false // Disable 상태면 비활성화
            }
        }

        Button(
            modifier = modifier,
            interactionSource = interactionSource,
            enabled = enabledState(state),
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.main500,
                contentColor = colors.white,
                disabledContainerColor = colors.gray200,//비활성화 버튼은 회색으로 처리
                disabledContentColor = colors.gray400
            ),
            contentPadding = PaddingValues(horizontal=22.dp, vertical = 13.dp),
            shape = RoundedCornerShape(12.dp),
            onClick = onClick
        ) {
            Text(
                text = text,
                style = typography.body1
            )
        }
    }
}

// 항상 활성화된 버튼 컴포저블 (텍스트 색상과 배경색을 인자로 받음)
@Composable
fun GwangSanEnableButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = Color.Black,
    backgroundColor: Color = Color.White,
    onClick: () -> Unit,
) {

    GwangSanTheme { colors, typography ->

        val interactionSource = remember { MutableInteractionSource() } // 상호작용 상태 저장

        // 실제 버튼 UI 정의
        Button(
            modifier = modifier,
            interactionSource = interactionSource,
            contentPadding = PaddingValues(horizontal=22.dp, vertical = 13.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor, // 배경색을 인자로 받음
                contentColor = colors.white,
            ),
            shape = RoundedCornerShape(6.dp), // 버튼 테두리 둥글기 설정
            onClick = onClick
        ) {
            Text(
                text = text,
                style = typography.body1,
                color = textColor
            )
        }
    }
}
