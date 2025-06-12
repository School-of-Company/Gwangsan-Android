package com.school_of_company.signin.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.componet.button.GwangSanEnableButton
import com.school_of_company.design_system.componet.button.GwangSanStateButton
import com.school_of_company.design_system.theme.GwangSanTheme
@Composable
internal fun StartRoute(
    onSignUpClick: () -> Unit,
    onInputLoginClick: () -> Unit
) {
    StartScreen(
        onSignUpClick = onSignUpClick,
        onInputLoginClick = onInputLoginClick
    )
}

@Composable
private fun StartScreen(
    modifier: Modifier = Modifier,
    onSignUpClick: () -> Unit,
    onInputLoginClick: () -> Unit
) {
    GwangSanTheme { colors, _ ->

        Box(
            modifier = modifier
                .fillMaxSize()
                .background(colors.white)
                .imePadding()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(24.dp)
            ) {
                GwangSanEnableButton(
                    text = "회원가입",
                    textColor = colors.main500,
                    backgroundColor = colors.white,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = colors.main500,
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    onSignUpClick()
                }

                Spacer(modifier = Modifier.height(12.dp))

                GwangSanStateButton(
                    text = "로그인",
                    modifier = Modifier.fillMaxWidth()
                ) {
                    onInputLoginClick()
                }
            }
        }
    }
}
@Preview
@Composable
private fun LoginInScreenPreview() {
    StartScreen(
        onSignUpClick = {},
        onInputLoginClick = {}
    )
}