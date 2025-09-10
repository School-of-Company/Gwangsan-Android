package com.school_of_company.signup.view

import android.content.ContextWrapper
import androidx.activity.ComponentActivity
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.school_of_company.Regex.isValidPassword
import com.school_of_company.design_system.component.button.GwangSanStateButton
import com.school_of_company.design_system.component.button.state.ButtonState
import com.school_of_company.design_system.component.clickable.GwangSanClickable
import com.school_of_company.design_system.component.icons.DownArrowIcon
import com.school_of_company.design_system.component.topbar.GwangSanTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.signup.viewmodel.SignUpViewModel
import com.school_of_company.ui.previews.GwangsanPreviews
import com.school_of_company.design_system.component.textfield.GwangSanTextField

@Composable
internal fun PasswordSignupRoute(
    onBackClick: () -> Unit,
    onCerTinSignUpClick: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel(
        viewModelStoreOwner = LocalContext.current.let { context ->
            var ctx = context
            while (ctx is ContextWrapper) {
                if (ctx is ComponentActivity) return@let ctx
                ctx = ctx.baseContext
            }
            ctx as ComponentActivity
        }
    )) {
    val password by viewModel.password.collectAsStateWithLifecycle()
    val rePassword by viewModel.checkPassword.collectAsStateWithLifecycle()

    val isPasswordValidError = password.isNotBlank() && !isValidPassword(password)
    val isPasswordMismatchError = rePassword.isNotBlank() && password != rePassword

    val isNextEnabled = !isPasswordValidError && !isPasswordMismatchError &&
            password.isNotBlank() && rePassword.isNotBlank()

    PasswordSignupScreen(
        onBackClick = onBackClick,
        onCerTinSignUpClick = onCerTinSignUpClick,
        isPasswordValidError = isPasswordValidError,
        isPasswordMismatchError = isPasswordMismatchError,
        isNextEnabled = isNextEnabled,
        password = password,
        rePassword = rePassword,
        onPasswordChange = viewModel::onPasswordChange,
        onRePasswordChange = viewModel::onCheckPasswordChange,
    )
}

@Composable
private fun PasswordSignupScreen(
    modifier: Modifier = Modifier,
    isPasswordValidError: Boolean,
    isPasswordMismatchError: Boolean,
    isNextEnabled: Boolean,
    password: String,
    onBackClick: () -> Unit,
    onCerTinSignUpClick: () -> Unit,
    rePassword: String,
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
                .padding(
                    top = 24.dp,
                    start = 24.dp,
                    end = 24.dp
                )
                .verticalScroll(scrollState)
                .pointerInput(Unit) {
                    detectTapGestures {
                        focusManager.clearFocus() }
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
                            top = 56.dp,
                            bottom = 32.dp
                        ),
                ) {
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
                    text = "비밀번호를 입력해주세요",
                    style = typography.label,
                    color = colors.black.copy(alpha = 0.5f),
                    fontWeight = FontWeight.Normal
                )

                Spacer(modifier = Modifier.height(48.dp))

                GwangSanTextField(
                    value = password,
                    label = "비밀번호",
                    placeHolder = "비밀번호",
                    isError = isPasswordValidError,
                    isDisabled = false,
                    errorText = "영문과 숫자를 특수문자를 포함한 8자 이상 입력해주세요.",
                    onTextChange = onPasswordChange,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.padding(16.dp))

                GwangSanTextField(
                    value = rePassword,
                    placeHolder = "비밀번호 재입력",
                    isError = isPasswordMismatchError,
                    isDisabled = false,
                    errorText = "비밀번호가 일치하지 않습니다.",
                    onTextChange = onRePasswordChange,
                    label = "비밀번호를 다시 입력해주세요",
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 64.dp),
            ) {
                GwangSanStateButton(
                    text = "다음",
                    state = if (isNextEnabled) ButtonState.Enable else ButtonState.Disable,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    onCerTinSignUpClick()
                }
            }
        }
    }
}

@GwangsanPreviews
@Composable
private fun PasswordSignupScreenPreview() {
    PasswordSignupScreen(
        password = "",
        rePassword = "",
        onBackClick = {},
        onCerTinSignUpClick = {},
        isPasswordValidError = false,
        isPasswordMismatchError = false,
        isNextEnabled = false,
        onPasswordChange = {},
        onRePasswordChange = {},
    )
}
