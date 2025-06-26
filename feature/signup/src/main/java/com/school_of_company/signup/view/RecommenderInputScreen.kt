package com.school_of_company.signup.view

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.school_of_company.design_system.componet.button.GwangSanStateButton
import com.school_of_company.design_system.componet.button.state.ButtonState
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.DownArrowIcon
import com.school_of_company.design_system.componet.topbar.GwangSanTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.signup.viewmodel.SignUpViewModel
import com.yourpackage.design_system.component.textField.GwangSanTextField

@Composable
internal fun ReCommenDerInputRoute(
    onBackClick: () -> Unit,
    onRecommenderClick: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val recommender by viewModel.recommender.collectAsStateWithLifecycle()
    val isNextEnabled = recommender.isNotBlank()

    RecommenderInputScreen(
        recommender = recommender,
        isNextEnabled = isNextEnabled,
        onRecommenderChange = viewModel::onRecommenderChange,
        onBackClick = onBackClick,
        onNextClick = onRecommenderClick
    )
}

@Composable
fun RecommenderInputScreen(
    onBackClick: () -> Unit,
    recommender: String,
    isNextEnabled: Boolean,
    onRecommenderChange: (String) -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
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
                onClick = onNextClick,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(start = 24.dp, end = 24.dp, bottom = 30.dp)
                    .fillMaxWidth()
            )
        }
    }
}