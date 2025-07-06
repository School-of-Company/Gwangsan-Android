package com.school_of_company.signup.view

import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import com.school_of_company.design_system.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.school_of_company.design_system.componet.button.GwangSanStateButton
import com.school_of_company.design_system.componet.button.state.ButtonState
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.DownArrowIcon
import com.school_of_company.design_system.componet.toast.makeToast
import com.school_of_company.design_system.componet.topbar.GwangSanTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.model.auth.request.SignUpCertificationNumberSendRequestModel
import com.school_of_company.signup.viewmodel.SignUpViewModel
import com.school_of_company.signup.viewmodel.uistate.SendNumberUiState
import com.school_of_company.signup.viewmodel.uistate.VerifyNumberUiState
import com.school_of_company.ui.previews.GwangsanPreviews
import com.yourpackage.design_system.component.textField.GwangSanTextField

@Composable
internal fun CertInSignUpRoute(
    onBackClick: () -> Unit,
    onNeighborhoodClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    viewModel: SignUpViewModel = hiltViewModel(
        viewModelStoreOwner = LocalContext.current.let { context ->
            var ctx = context
            while (ctx is ContextWrapper) {
                if (ctx is ComponentActivity) return@let ctx
                ctx = ctx.baseContext
            }
            ctx as ComponentActivity
        }
    )
) {

    val certificationNumber by viewModel.certificationNumber.collectAsStateWithLifecycle()
    val phoneNumber by viewModel.number.collectAsStateWithLifecycle()
    val verifyNumberUiState by viewModel.verifyNumberUiState.collectAsStateWithLifecycle()
    val sendNumberUiState by viewModel.sendNumberUiState.collectAsStateWithLifecycle()

    var isLoading by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }

    val isVerifyNumberError = verifyNumberUiState is VerifyNumberUiState.Error

    val context = LocalContext.current

    LaunchedEffect(verifyNumberUiState) {
        when (verifyNumberUiState) {
            is VerifyNumberUiState.Loading -> Unit
            is VerifyNumberUiState.Success -> onNeighborhoodClick()
            is VerifyNumberUiState.BadRequest -> {
                isLoading = false
                isError = true
                onErrorToast(null, R.string.error_id_not_valid)
            }

            is VerifyNumberUiState.NotFound -> {
                isLoading = false
                isError = true
                onErrorToast(null, R.string.error_too_many_request_send_email)
            }

            is VerifyNumberUiState.TooManyRequest -> {
                isLoading = false
                isError = true
                onErrorToast(null, R.string.error_send_number)
            }

            is VerifyNumberUiState.Error -> {
                isLoading = false
                isError = true
                onErrorToast(
                    (verifyNumberUiState as VerifyNumberUiState.Error).exception,
                    R.string.error_verify_number
                )
            }
        }
    }

    LaunchedEffect(sendNumberUiState) {
        when (sendNumberUiState) {
            is SendNumberUiState.Loading -> Unit
            is SendNumberUiState.Success -> makeToast(context, "인증번호 성공")
            is SendNumberUiState.PhoneNumberNotValid -> {
                isLoading = false
                onErrorToast(null, R.string.error_id_not_valid)
            }

            is SendNumberUiState.TooManyRequest -> {
                isLoading = false
                onErrorToast(null, R.string.error_too_many_request_send_email)
            }

            is SendNumberUiState.Error -> {
                isLoading = false
                onErrorToast(
                    (sendNumberUiState as SendNumberUiState.Error).exception,
                    R.string.error_send_number
                )
            }
        }
    }

    CertinSignUpScreen(
        onBackClick = onBackClick,
        certificationCallBack = {
            viewModel.verifyNumber(
                phoneNumber = phoneNumber,
                code = certificationNumber
            )
        },
        sendCertificationCodeCallBack = {
            viewModel.sendCertificationCode(
                body = SignUpCertificationNumberSendRequestModel(
                    phoneNumber = phoneNumber
                )
            )
        },
        onPhoneNumberChange = viewModel::onNumberChange,
        onCertificationNumberChange = viewModel::onCertificationNumberChange,
        phoneNumber = phoneNumber,
        certificationNumber = certificationNumber,
        verifyNumberUiState = verifyNumberUiState,
        isVerifyNumberError = isVerifyNumberError
    )
}

@Composable
private fun CertinSignUpScreen(
    modifier: Modifier = Modifier,
    phoneNumber: String,
    verifyNumberUiState: VerifyNumberUiState,
    isVerifyNumberError: Boolean,
    certificationNumber: String,
    onBackClick: () -> Unit,
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
                .padding(24.dp)
                .verticalScroll(scrollState)
                .pointerInput(Unit) {
                    detectTapGestures { focusManager.clearFocus() }
                }
        ) {
            GwangSanTopBar(
                startIcon = { DownArrowIcon(modifier = Modifier.GwangSanClickable { onBackClick() }) },
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
                    isDisabled = false,
                    errorText = "",
                    onTextChange = onPhoneNumberChange,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .weight(1f)
                        .height(70.dp)
                )

                GwangSanStateButton(
                    text = "인증",
                    state = if (phoneNumber.isNotBlank()) ButtonState.Enable else ButtonState.Disable,
                    modifier = Modifier
                        .width(80.dp)
                        .height(50.dp)
                        .align(Alignment.Bottom)
                ) {
                    sendCertificationCodeCallBack()
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            GwangSanTextField(
                value = certificationNumber,
                placeHolder = "인증번호를 입력해주세요",
                isError = isVerifyNumberError,
                isDisabled = false,
                errorText = "인증번호가 틀립니다..",
                onTextChange = {
                    if (it.length <= 6 && it.all { char -> char.isDigit() }) onCertificationNumberChange(
                        it
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = "전화번호 인증",
                modifier = Modifier.fillMaxWidth()
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
                state = when {
                    verifyNumberUiState is VerifyNumberUiState.Success -> ButtonState.Enable
                    certificationNumber.isNotBlank() -> ButtonState.Enable
                    else -> ButtonState.Disable
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                certificationCallBack()
            }
        }
    }
}

@GwangsanPreviews
@Composable
private fun CertinSignUpScreenPreview() {
    CertinSignUpScreen(
        onBackClick = {},
        onNeighborhoodClick = {},
        certificationCallBack = {},
        sendCertificationCodeCallBack = {},
        onErrorToast = { _, _ -> },
        onPhoneNumberChange = {},
        onCertificationNumberChange = {},
        phoneNumber = "",
        certificationNumber = "",
        verifyNumberUiState = VerifyNumberUiState.Loading,
        sendNumberUiState = SendNumberUiState.Loading
    )
}
