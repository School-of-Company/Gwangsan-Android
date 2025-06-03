package com.school_of_company.signin.view

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
private fun SignInScreen(
    modifier: Modifier = Modifier,
    isEmailError: Boolean,
    isPasswordError: Boolean,
    id: String,
    password: String,
    focusManager: FocusManager = LocalFocusManager.current,
    scrollState: ScrollState = rememberScrollState(),
    signInCallBack: () -> Unit,
    onIdChange: (String) -> Unit,
) {
    GwangSanTheme { colors, typography ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
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
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top =18.dp,
                            bottom = 32.dp
                        )
                    ,
                ){
                    GwangSanTopBar(
                        startIcon = { DownArrowIcon(modifier = Modifier.GwangSanClickable {  }) },
                        betweenText = "뒤로"
                    )
                }
                Text(
                    text = "로그인",
                    style = typography.titleMedium2,
                    color = colors.black,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "별칭을 입력해주세요",
                    style = typography.label,
                    color = colors.black.copy(alpha = 0.5f),
                    fontWeight = FontWeight.Normal
                )

                Spacer(modifier = Modifier.height(48.dp))

                GwangSanTextField(
                    placeHolder = "별칭",
                    onTextChange = onIdChange,
                    label = "별칭을 입력해주세요",
                    isError = isEmailError,
                    errorText = "유효하지 않은 별칭입니다",
                    modifier = modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.padding(15.dp))

                GwangSanTextField(
                    placeHolder = "비밀번호",
                    onTextChange = onIdChange,
                    label = "비밀번호를 입력해주세요",
                    isError = isPasswordError,
                    errorText = "유효하지 않은 비밀번호입니다",
                    modifier = modifier.fillMaxWidth(),
                )
            }

            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 64.dp)
            ) {
                GwangSanStateButton(
                    text = "로그인",
                    state = if (id.isNotBlank() && password.isNotBlank()) ButtonState.Enable else ButtonState.Disable,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    signInCallBack()
                }
            }
        }
    }
}
@Preview
@Composable
private fun SignInScreenPreview() {
    SignInScreen(
        id = "",
        password = "",
        onIdChange = {},
        signInCallBack = {},
        isEmailError = false,
        isPasswordError = false
    )
}