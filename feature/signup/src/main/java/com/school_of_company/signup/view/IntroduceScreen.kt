package com.school_of_company.signup.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.school_of_company.design_system.R
import com.school_of_company.design_system.componet.button.GwangSanButton
import com.school_of_company.design_system.componet.button.GwangSanStateButton
import com.school_of_company.design_system.componet.button.state.ButtonState
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.DownArrowIcon
import com.school_of_company.design_system.componet.topbar.GwangSanTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.design_system.theme.color.GwangSanColor
import com.school_of_company.model.auth.request.SignUpRequestModel
import com.school_of_company.model.enum.Branch
import com.school_of_company.model.enum.Dong
import com.school_of_company.model.enum.Specialty
import com.school_of_company.signup.viewmodel.SignUpViewModel
import com.school_of_company.signup.viewmodel.uistate.SignUpUiState
import com.yourpackage.design_system.component.textField.GwangSanSelectTextField
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun IntroduceRoute(
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val specialty by viewModel.specialty.collectAsStateWithLifecycle()
    val isDropdownVisible by viewModel.specialtyDropdownVisible.collectAsStateWithLifecycle()
    val signUpUiState by viewModel.signUpUiState.collectAsStateWithLifecycle()

    LaunchedEffect(signUpUiState) {
        when (signUpUiState) {
            is SignUpUiState.Success -> {
                Log.d("IntroduceRoute", "회원가입 성공 → RecommenderScreen으로 이동")
                onNextClick()
            }
            is SignUpUiState.Error -> {
                Log.e("IntroduceRoute", "회원가입 실패: ${(signUpUiState as SignUpUiState.Error).exception}")
                onErrorToast((signUpUiState as SignUpUiState.Error).exception, R.string.error_generic)
            }
            is SignUpUiState.Loading -> {
                Log.d("IntroduceRoute", "회원가입 진행 중...")
            }
            is SignUpUiState.Conflict -> {
                Log.e("IntroduceRoute", "회원가입 충돌: 사용자 이미 존재")
                onErrorToast(null, R.string.error_user_exists)
            }
            is SignUpUiState.PasswordMismatch -> {
                Log.e("IntroduceRoute", "비밀번호 불일치")
                onErrorToast(null, R.string.error_password_mismatch)
            }
            is SignUpUiState.PasswordNotValid -> {
                Log.e("IntroduceRoute", "비밀번호 유효하지 않음")
                onErrorToast(null, R.string.error_invalid_password)
            }
            is SignUpUiState.BadRequest -> {
                Log.e("IntroduceRoute", "잘못된 요청")
                onErrorToast(null, R.string.error_bad_request)
            }
            is SignUpUiState.NotFound -> {
                Log.e("IntroduceRoute", "리소스 없음")
                onErrorToast(null, R.string.error_resource_not_found)
            }
            is SignUpUiState.TooManyRequest -> {
                Log.e("IntroduceRoute", "요청이 너무 많음")
                onErrorToast(null, R.string.error_too_many_requests)
            }
        }
    }

    IntroduceScreen(
        specialty = specialty,
        isDropdownVisible = isDropdownVisible,
        onBackClick = onBackClick,
        onNextClick = onNextClick,
        onToggleDropdown = viewModel::toggleSpecialtyDropdown,
        onCloseDropdown = viewModel::closeSpecialtyDropdown,
        onSpecialtyChange = viewModel::onSpecialtyChange,
        callBack = {

            val branchEnum = Branch.from(viewModel.branch.value)
            val dongEnum = Dong.from(viewModel.dong.value)
            val specialtyEnum = Specialty.from(viewModel.specialty.value)

            viewModel.signUp(
                SignUpRequestModel(
                    name = viewModel.name.value,
                    nickname = viewModel.nickname.value,
                    password = viewModel.password.value,
                    phoneNumber = viewModel.number.value,
                    verificationCode = viewModel.certificationNumber.value,
                    dong = dongEnum,
                    branch = branchEnum,
                    recommender = viewModel.recommender.value,
                    introduction = viewModel.specialty.value,
                    specialty = specialtyEnum
                )
            )
        }
    )
}

@Composable
private fun IntroduceScreen(
    modifier: Modifier = Modifier,
    specialty: String,
    isDropdownVisible: Boolean,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    onToggleDropdown: () -> Unit,
    onCloseDropdown: () -> Unit,
    callBack: () -> Unit,
    onSpecialtyChange: (String) -> Unit
) {
    val introduce = specialty.split(", ").filter { it.isNotBlank() }
    val backgroundColor = if (isDropdownVisible) GwangSanColor.gray300 else GwangSanColor.white
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    GwangSanTheme { colors, typography ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(backgroundColor)
                .imePadding()
                .pointerInput(isDropdownVisible) {
                    detectTapGestures {
                        if (isDropdownVisible) {
                            onCloseDropdown()
                        } else {
                            focusManager.clearFocus()
                        }
                    }
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, bottom = 32.dp),
                    horizontalArrangement = Arrangement.Start
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
                    text = "자신을 소개하는 글을 작성해주세요",
                    style = typography.label,
                    color = colors.black.copy(alpha = 0.5f)
                )

                Spacer(modifier = Modifier.height(32.dp))

                GwangSanSelectTextField(
                    label = "특기",
                    value = introduce.joinToString(", "),
                    placeHolder = "특기 추가",
                    onClick = onToggleDropdown,
                    onTextChange = {}
                )

                if (isDropdownVisible) {
                    Spacer(modifier = Modifier.height(8.dp))

                    MultiSelectDropdown(
                        options = listOf("청소하기", "운전하기", "달리기", "빨래하기", "벌레잡기", "이삿짐 나르기"),
                        selectedOptions = introduce,
                        onSelectionChange = {
                            onSpecialtyChange(it.joinToString(", "))
                        },
                        onDismissRequest = onCloseDropdown,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isDropdownVisible) colors.gray200 else colors.background)
                ) {
                    if (isDropdownVisible) {
                        GwangSanButton(
                            text = "다음",
                            color = colors.gray400,
                            textColor = colors.gray500,
                            onClick = {},
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                        )
                    } else {
                        GwangSanStateButton(
                            text = "다음",
                            state = if (specialty.isNotBlank()) ButtonState.Enable else ButtonState.Disable,
                            onClick = {
                                callBack()
                                onNextClick()
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}
