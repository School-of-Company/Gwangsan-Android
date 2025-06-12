package com.school_of_company.signup.view

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.componet.button.GwangSanStateButton
import com.school_of_company.design_system.componet.button.state.ButtonState
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.DownArrowIcon
import com.school_of_company.design_system.componet.topbar.GwangSanTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.yourpackage.design_system.component.textField.GwangSanTextField

@Composable
internal fun PasswordSignupRoute(
    onBackClick: () -> Unit,
    onCerTinSignUpClick: () -> Unit
) {
    PasswordSignupScreen(
        onBackClick = onBackClick,
        onCerTinSignUpClick = onCerTinSignUpClick,
        isPasswordValidError = false,
        isPasswordMismatchError = false,
        password = "",
        rePassword = "",
        onPasswordChange = {},
        onRePasswordChange = {},
        signInCallBack = {}
    )
}

@Composable
private fun PasswordSignupScreen(
    modifier: Modifier = Modifier,
    isPasswordValidError: Boolean,
    isPasswordMismatchError: Boolean,
    password: String,
    onBackClick: () -> Unit,
    onCerTinSignUpClick: () -> Unit,
    rePassword: String,
    signInCallBack: () -> Unit,
    focusManager: FocusManager = LocalFocusManager.current,
    scrollState: ScrollState = rememberScrollState(),
    onPasswordChange: (String) -> Unit,
    onRePasswordChange: (String) -> Unit,

) {
    GwangSanTheme { colors, typography ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .background(color = colors.white)
                .imePadding()
                .padding(
                    top = 24.dp,
                    start = 24.dp,
                    end = 24.dp
                )
                .verticalScroll(scrollState)
                .pointerInput(Unit) {
                    detectTapGestures {
                        focusManager.clearFocus()
                    }
                }
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = modifier.fillMaxWidth(),
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(
                            top =18.dp,
                            bottom = 32.dp
                        )
                    ,
                ){
                    GwangSanTopBar(
                        startIcon = { DownArrowIcon(modifier = Modifier.GwangSanClickable { onBackClick() }) },
                        betweenText = "뒤로"
                    )
                }
                Text(
                    text = "회원가입",
                    style = typography.titleMedium2,
                    color = colors.black,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "비밀번호을 입력해주세요",
                    style = typography.label,
                    color = colors.black.copy(alpha = 0.5f),
                    fontWeight = FontWeight.Normal
                )

                Spacer(modifier = Modifier.height(48.dp))

                GwangSanTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp),
                    value = password,
                    label = "비밀번호",
                    placeHolder = "비밀번호",
                    isError = isPasswordValidError,
                    isDisabled = false,
                    errorText = "유효하지 않은 빌밀번호입니다.",
                    onTextChange = onPasswordChange,
                )

                Spacer(modifier = Modifier.padding(16.dp))

                GwangSanTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp),
                    value = rePassword,
                    placeHolder = "비밀번호 재입력",
                    isError = isPasswordMismatchError,
                    isDisabled = false,
                    errorText = "비밀번호가 틀립니다.",
                    onTextChange = onRePasswordChange,
                    label = "비밀번호를 다시 입력해주세요"
                )
            }
            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 64.dp), // 필요 시 여백 추가

            ) {
                GwangSanStateButton(
                    text = "다음",
                    state = if (password.isNotBlank() && rePassword.isNotBlank()) ButtonState.Enable else ButtonState.Disable,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    signInCallBack()
                    onCerTinSignUpClick()
                }
            }
        }
    }
}

@Preview
@Composable
fun PasswordSignupScreenPreview(){
    PasswordSignupScreen(
        isPasswordValidError = false,
        isPasswordMismatchError = false,
        password = "",
        rePassword = "",
        onPasswordChange = {},
        onRePasswordChange = {},
        signInCallBack = {},
        onBackClick = {},
        onCerTinSignUpClick = {}
    )
}