package com.school_of_company.signin.view

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.school_of_company.design_system.R
import com.school_of_company.design_system.componet.button.GwangSanStateButton
import com.school_of_company.design_system.componet.button.state.ButtonState
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.DownArrowIcon
import com.school_of_company.design_system.componet.topbar.GwangSanTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.network.util.DeviceIdManager
import com.school_of_company.signin.viewmodel.SignInViewModel
import com.school_of_company.signin.viewmodel.uistate.SaveTokenUiState
import com.school_of_company.signin.viewmodel.uistate.SignInUiState
import com.yourpackage.design_system.component.textField.GwangSanTextField

@Composable
internal fun SignInRoute(
    onBackClick: () -> Unit,
    onMainClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    viewModel: SignInViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val signInUiState by viewModel.signInUiState.collectAsStateWithLifecycle()
    val saveTokenUiState by viewModel.saveTokenUiState.collectAsStateWithLifecycle()
    val id by viewModel.id.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val deviceId = remember { DeviceIdManager.getDeviceId(context) }

    var idIsError by remember { mutableStateOf(false) }
    var passwordIsError by remember { mutableStateOf(false) }

    LaunchedEffect(signInUiState, saveTokenUiState) {
        when (signInUiState) {
            is SignInUiState.Loading -> {
                idIsError = false
                passwordIsError = false
            }

            is SignInUiState.Success -> {
                when (saveTokenUiState) {
                    is SaveTokenUiState.Loading -> Unit
                    is SaveTokenUiState.Success -> onMainClick()
                    is SaveTokenUiState.Error -> Unit
                }
            }

            is SignInUiState.NotFound -> {
                idIsError = true
                onErrorToast(null, R.string.error_user_missing)
            }

            is SignInUiState.IdNotValid -> {
                idIsError = true
                onErrorToast(null, R.string.error_id_not_valid)
            }

            is SignInUiState.Error -> {
                val signInUiStateError= signInUiState as SignInUiState.Error
                onErrorToast(signInUiStateError.exception, R.string.error_login)
            }
        }
    }

    SignInScreen(
        id = id,
        password = password,
        idIsError = idIsError,
        passwordIsError = passwordIsError,
        onIdChange = viewModel::onIdChange,
        onPasswordChange = viewModel::onPasswordChange,
        onBackClick = onBackClick,
        signInCallBack = {
            viewModel.login(deviceId = deviceId)
        }
    )
}

@Composable
private fun SignInScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    signInCallBack: () -> Unit,
    id: String,
    password: String,
    onIdChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    idIsError: Boolean = false,
    passwordIsError: Boolean = false
) {
    val focusManager = LocalFocusManager.current

    GwangSanTheme { colors, typography ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
                .padding(
                    top = 24.dp,
                    start = 24.dp,
                    end = 24.dp,
                    bottom = 50.dp
                )
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
                            top = 18.dp,
                            bottom = 32.dp
                        )
                ) {
                    GwangSanTopBar(
                        startIcon = { DownArrowIcon(modifier = Modifier.GwangSanClickable { onBackClick() }) },
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
                    color = colors.black.copy(alpha = 0.5f)
                )

                Spacer(modifier = Modifier.height(48.dp))

                GwangSanTextField(
                    placeHolder = "별칭",
                    value = id,
                    onTextChange = onIdChange,
                    isError = idIsError,
                    label = "별칭을 입력해주세요",
                    errorText = "유효하지 않은 별칭입니다",
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.padding(15.dp))

                GwangSanTextField(
                    placeHolder = "비밀번호",
                    value = password,
                    onTextChange = onPasswordChange,
                    isError = passwordIsError,
                    label = "비밀번호를 입력해주세요",
                    errorText = "유효하지 않은 비밀번호입니다",
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.weight(1f))

                GwangSanStateButton(
                    text = "로그인",
                    state = if (id.isNotBlank() && password.isNotBlank()) ButtonState.Enable else ButtonState.Disable,
                    onClick = signInCallBack,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.padding(bottom = 50.dp))
            }
        }
    }
}
