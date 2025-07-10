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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
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
import com.school_of_company.ui.previews.GwangsanPreviews
import com.yourpackage.design_system.component.textField.GwangSanTextField

@Composable
internal fun NameSignupRoute(
    onBackClick: () -> Unit,
    onNicknameClick: ()-> Unit,
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
    val name by viewModel.name.collectAsStateWithLifecycle()

    NameSignupScreen(
        onBackClick = onBackClick,
        onPasswordClick = onNicknameClick,
        id = name,
        onIdChange = viewModel::onNameChange,
    )
}

@Composable
private fun NameSignupScreen(
    modifier: Modifier = Modifier,
    id: String,
    onBackClick: () -> Unit,
    onPasswordClick: ()-> Unit,
    focusManager: FocusManager = LocalFocusManager.current,
    scrollState: ScrollState = rememberScrollState(),
    onIdChange: (String) -> Unit,
) {
    GwangSanTheme { colors, typography ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
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
                            top = 56.dp,
                            bottom = 32.dp
                        ),
                ){
                    GwangSanTopBar(
                        startIcon = { DownArrowIcon(modifier = Modifier.GwangSanClickable {onBackClick()}) },
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
                    text = "이름을 입력해주세요",
                    style = typography.label,
                    color = colors.black.copy(alpha = 0.5f),
                    fontWeight = FontWeight.Normal
                )

                Spacer(modifier = Modifier.height(48.dp))

                GwangSanTextField(
                    placeHolder = "이름",
                    value = id,
                    onTextChange = onIdChange,
                    label = "이름을 입력해주세요",
                    isDisabled = false,
                    keyboardOptions = KeyboardOptions.Default,
                    isError = false,
                    errorText = "유효하지 않은 이름입니다",
                    modifier = Modifier.fillMaxWidth()
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
                    state = if (id.isNotBlank()) ButtonState.Enable else ButtonState.Disable,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    onPasswordClick()
                }
            }
        }
    }
}

@GwangsanPreviews
@Composable
fun  NameSignupScreenPreview(
){
    NameSignupScreen(
        id = "",
        onIdChange = {},
        onBackClick = {},
        onPasswordClick = {}
    )
}
