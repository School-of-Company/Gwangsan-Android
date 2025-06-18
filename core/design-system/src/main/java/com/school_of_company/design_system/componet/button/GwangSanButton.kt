package com.school_of_company.design_system.componet.button

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.R
import com.school_of_company.design_system.componet.button.state.ButtonState
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.theme.GwangSanTheme

@Composable
fun GwangSanButton(
    modifier: Modifier = Modifier,
    text: String,
    color: Color,
    textColor: Color,
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
                .GwangSanClickable(
                    onClick = onClick,
                    rippleColor = colors.white
                )
                .then(modifier)
        ) {
            Text(
                text = text,
                style = typography.body2,
                fontWeight = FontWeight.SemiBold,
                color = textColor
            )
        }
    }
}

@Composable
fun GwangSanStateButton(
    modifier: Modifier = Modifier,
    text: String,
    state: ButtonState = ButtonState.Enable,
    onClick: () -> Unit,
) {
    GwangSanTheme { colors, typography ->

        val interactionSource = remember { MutableInteractionSource() }


        val enabledState: (buttonState: ButtonState) -> Boolean = {
            when (it) {
                ButtonState.Enable -> true
                ButtonState.Disable -> false
            }
        }

        Button(
            modifier = modifier,
            interactionSource = interactionSource,
            enabled = enabledState(state),
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.main500,
                contentColor = colors.white,
                disabledContainerColor = colors.gray200,
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


@Composable
fun GwangSanEnableButton(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle? = null,
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
                containerColor = backgroundColor,
                contentColor = colors.white,
            ),
            shape = RoundedCornerShape(8.dp),
            onClick = onClick
        ) {
            Text(
                text = text,
                style = textStyle ?: typography.body1,
                color = textColor
            )
        }
    }
}

@Composable
fun ChatSendButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    GwangSanTheme { colors, _ ->
        Box(
            modifier = modifier
                .size(44.dp)
                .background(color = colors.subYellow500, shape = CircleShape)
                .GwangSanClickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_down),
                contentDescription = "메시지 전송",
                tint = colors.white
            )
        }
    }
}

@Preview
@Composable
fun ButtonPreView(){
    GwangSanStateButton(
        text = "버튼",
        state = ButtonState.Enable,
        onClick = {}
    )
}