package com.school_of_company.signup.view

import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import com.school_of_company.design_system.R
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
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
import com.school_of_company.model.auth.request.SignUpRequestModel
import com.school_of_company.signup.viewmodel.SignUpViewModel
import com.school_of_company.signup.viewmodel.uistate.SignUpUiState
import com.school_of_company.ui.previews.GwangsanPreviews
import com.yourpackage.design_system.component.textField.GwangSanTextField

@Composable
internal fun ReCommenDerInputRoute(
    onBackClick: () -> Unit,
    onRecommenderClick: () -> Unit,
    onErrorToast: (Throwable?, Int?) -> Unit,
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
    val signUpUiState by viewModel.signUpUiState.collectAsStateWithLifecycle()
    val recommender by viewModel.recommender.collectAsStateWithLifecycle()

    val isNextEnabled = recommender.isNotBlank()

    val context = LocalContext.current

    LaunchedEffect(signUpUiState) {
        when (signUpUiState) {
            is SignUpUiState.Success -> {
                makeToast(context, "회원가입 성공")
                onRecommenderClick()
            }
            is SignUpUiState.Error -> {
                onErrorToast((signUpUiState as SignUpUiState.Error).exception, R.string.error_generic)
            }
            is SignUpUiState.Loading -> Unit
            is SignUpUiState.Conflict -> {
                onErrorToast(null, R.string.error_user_exists)
            }
            is SignUpUiState.PasswordMismatch -> {
                onErrorToast(null, R.string.error_password_mismatch)
            }
            is SignUpUiState.PasswordNotValid -> {
                onErrorToast(null, R.string.error_invalid_password)
            }
            is SignUpUiState.BadRequest -> {
                onErrorToast(null, R.string.error_bad_request)
            }
            is SignUpUiState.NotFound -> {
                onErrorToast(null, R.string.error_resource_not_found)
            }
            is SignUpUiState.TooManyRequest -> {
                onErrorToast(null, R.string.error_too_many_requests)
            }
        }
    }

    RecommenderInputScreen(
        recommender = recommender,
        isNextEnabled = isNextEnabled,
        onRecommenderChange = viewModel::onRecommenderChange,
        onBackClick = onBackClick,
        onSignUpCallBack = {
            viewModel.signUp(
                body = SignUpRequestModel(
                    name = viewModel.name.value,
                    nickname = viewModel.nickname.value,
                    password = viewModel.password.value,
                    phoneNumber = viewModel.number.value,
                    dongName = viewModel.dong.value,
                    placeName = viewModel.placeName.value,
                    specialties = viewModel.specialty.value,
                    description = viewModel.description.value,
                    recommender = viewModel.recommender.value,
                )
            )
        }
    )
}

@Composable
fun RecommenderInputScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    recommender: String,
    isNextEnabled: Boolean,
    onRecommenderChange: (String) -> Unit,
    onSignUpCallBack: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    GwangSanTheme { colors, typography ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(colors.white)
                .pointerInput(Unit) {
                    detectTapGestures { focusManager.clearFocus() }
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState())
                    .imePadding()
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                GwangSanTopBar(
                    startIcon = {
                        DownArrowIcon(modifier = Modifier.GwangSanClickable { onBackClick() })
                    },
                    betweenText = "뒤로"
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "회원가입",
                    style = typography.titleMedium2,
                    fontWeight = FontWeight.Bold,
                    color = colors.black
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "추천인의 별칭을 작성해주세요",
                    style = typography.label,
                    color = colors.black.copy(alpha = 0.5f)
                )

                Spacer(modifier = Modifier.height(32.dp))

                GwangSanTextField(
                    label = "추천인",
                    value = recommender,
                    placeHolder = "추천인 별칭을 입력해주세요",
                    onTextChange = onRecommenderChange
                )
            }

            GwangSanStateButton(
                text = "다음",
                state = if (isNextEnabled) ButtonState.Enable else ButtonState.Disable,
                onClick = onSignUpCallBack,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(start = 24.dp, end = 24.dp, bottom = 30.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@GwangsanPreviews
@Composable
private fun RecommenderInputScreenPreview() {
    RecommenderInputScreen(
        recommender = "",
        isNextEnabled = false,
        onRecommenderChange = {},
        onBackClick = {},
        onSignUpCallBack = {}
    )
}