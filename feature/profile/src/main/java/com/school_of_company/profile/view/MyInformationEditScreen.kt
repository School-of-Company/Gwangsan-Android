package com.school_of_company.profile.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.school_of_company.design_system.componet.button.GwangSanStateButton
import com.school_of_company.design_system.componet.button.state.ButtonState
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.CloseIcon
import com.school_of_company.design_system.componet.icons.DownArrowIcon
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.model.member.request.ModifyMemberInformationRequestModel
import com.school_of_company.profile.viewmodel.MyProfileViewModel
import com.school_of_company.profile.viewmodel.uistate.MyInForMatIonPeTchUiState
import com.yourpackage.design_system.component.textField.GwangSanTextField

@Composable
internal fun MyInformationEditRoute(
    onBackClick: () -> Unit,
    onSubmitComplete: () -> Unit,
    onErrorToast: (Throwable?, Int?) -> Unit,
    viewModel: MyProfileViewModel = hiltViewModel()
) {
    val nickname by viewModel.nickname.collectAsStateWithLifecycle()
    val specialty by viewModel.specialty.collectAsStateWithLifecycle()
    val description by viewModel.description.collectAsStateWithLifecycle()
    val myInForMatIonPeTchUiState by viewModel.myInformationPatchUiState.collectAsStateWithLifecycle()

    LaunchedEffect(myInForMatIonPeTchUiState) {
        when (myInForMatIonPeTchUiState) {
            is MyInForMatIonPeTchUiState.Success -> onSubmitComplete()
            is MyInForMatIonPeTchUiState.Loading -> Unit
            is MyInForMatIonPeTchUiState.Error -> {
                val exception = (myInForMatIonPeTchUiState as MyInForMatIonPeTchUiState.Error).exception
                onErrorToast(exception, null)
            }
            null -> Unit
        }
    }

    MyInformationEditScreen(
        nickname = nickname,
        onNicknameChange = viewModel::onNickNameChange,
        specialty = specialty,
        onSpecialtyChange = viewModel::onSpecialtyChange,
        description = description,
        onDescriptionChange = viewModel::onDescriptionChange,
        onSubmitClick = {
            viewModel.myInformationPatch(
                ModifyMemberInformationRequestModel(
                    nickname = nickname,
                    specialties = listOf(specialty),
                    description = description
                )
            )
        },
        onBackClick = onBackClick
    )
}

@Composable
fun MyInformationEditScreen(
    modifier: Modifier = Modifier,
    nickname: String,
    onNicknameChange: (String) -> Unit,
    specialty: String,
    onSpecialtyChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    onSubmitClick: () -> Unit,
    onBackClick: () -> Unit
) {
    GwangSanTheme { colors, typography ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colors.white)
                .padding(24.dp)
        ) {
            GwangSanSubTopBar(
                startIcon = { DownArrowIcon(modifier = Modifier.GwangSanClickable { onBackClick() }) },
                betweenText = "내 정보 수정",
                endIcon = { CloseIcon(modifier = Modifier.GwangSanClickable { onBackClick() }) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            GwangSanTextField(
                value = nickname,
                onTextChange = onNicknameChange,
                placeHolder = "별칭을 입력해주세요",
                label = "별칭",
                isError = false,
                errorText = "",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            GwangSanTextField(
                value = specialty,
                onTextChange = onSpecialtyChange,
                placeHolder = "특기를 입력해주세요",
                label = "특기",
                isError = false,
                errorText = "",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            GwangSanTextField(
                value = description,
                onTextChange = onDescriptionChange,
                placeHolder = "자기소개를 입력해주세요",
                label = "자기소개",
                isError = false,
                errorText = "",
                singleLine = false,
                maxLines = 6,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            GwangSanStateButton(
                text = "수정",
                state = if (nickname.isNotBlank() && description.isNotBlank()) ButtonState.Enable else ButtonState.Disable,
                onClick = onSubmitClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )
        }
    }
}

@Composable
@androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "MyInformationEditScreen Preview")
fun MyInformationEditScreenPreview() {
    MyInformationEditScreen(
        nickname = "홍길동",
        onNicknameChange = {},
        specialty = "앱 개발",
        onSpecialtyChange = {},
        description = "열정적인 개발자입니다.",
        onDescriptionChange = {},
        onSubmitClick = {},
        onBackClick = {}
    )
}