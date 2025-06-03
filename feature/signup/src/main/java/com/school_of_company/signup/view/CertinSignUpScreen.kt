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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
private fun CerTinSignUpScreen(
    modifier: Modifier = Modifier,
    isCertificationCodeError: Boolean,
    password: String,
    rePassword: String,
    phoneNumber: String,
    certificationNumber: String,
    focusManager: FocusManager = LocalFocusManager.current,
    scrollState: ScrollState = rememberScrollState(),
    certificationCallBack: () -> Unit,
    sendCertificationCodeCallBack: () -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onCertificationNumberChange: (String) -> Unit,
) {
    GwangSanTheme { colors, typography ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
                .imePadding()
                .padding(24.dp)
                .verticalScroll(scrollState)
                .pointerInput(Unit) {
                    detectTapGestures { focusManager.clearFocus() }
                }
        ) {
            GwangSanTopBar(
                startIcon = { DownArrowIcon(modifier = Modifier.GwangSanClickable { }) },
                betweenText = "뒤로"
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "회원가입",
                style = typography.titleMedium2,
                color = colors.black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "전화번호를 입력해주세요",
                style = typography.label,
                color = colors.black.copy(alpha = 0.5f),
                fontWeight = FontWeight.Normal,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                GwangSanTextField(
                    value = phoneNumber,
                    label = "전화번호",
                    placeHolder = "연락처는 \" - \" 빼고 입력해주세요",
                    isError = false,
                    isDisabled = false,
                    errorText = "",
                    onTextChange = onPhoneNumberChange,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp),
                )

                GwangSanStateButton(
                    text = "인증",
                    state = if (phoneNumber.isNotBlank()) ButtonState.Enable else ButtonState.Disable,
                    modifier = Modifier
                        .height(54.dp)
                ) {
                    sendCertificationCodeCallBack()
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            GwangSanTextField(
                value = certificationNumber,
                placeHolder = "전화번호 인증",
                isError = isCertificationCodeError,
                isDisabled = false,
                errorText = "인증번호가 틀립니다..",
                onTextChange = {
                    if (it.length <= 6 && it.all { char -> char.isDigit() }) onCertificationNumberChange(it)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = "비밀번호 확인",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp),
            )
        }
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    bottom = 64.dp
                ),
        ) {
            GwangSanStateButton(
                text = "인증하기",
                state = if (password.isNotBlank() && rePassword.isNotBlank()) ButtonState.Enable else ButtonState.Disable,
                modifier = Modifier.fillMaxWidth()
            ) {
                certificationCallBack()
            }
        }
    }
}

@Preview
@Composable
fun CerTinSignUpScreenPreview(){
    CerTinSignUpScreen(
        isCertificationCodeError = false,
        password = "",
        rePassword = "",
        phoneNumber = "",
        certificationNumber = "",
        onPhoneNumberChange = {},
        onCertificationNumberChange = {},
        certificationCallBack = {},
        sendCertificationCodeCallBack = {}
    )
}