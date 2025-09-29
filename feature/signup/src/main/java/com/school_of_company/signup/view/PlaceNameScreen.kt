package com.school_of_company.signup.view

import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.school_of_company.design_system.component.button.GwangSanButton
import com.school_of_company.design_system.component.button.GwangSanStateButton
import com.school_of_company.design_system.component.button.state.ButtonState
import com.school_of_company.design_system.component.clickable.GwangSanClickable
import com.school_of_company.design_system.component.icons.DownArrowIcon
import com.school_of_company.design_system.component.topbar.GwangSanTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.design_system.theme.color.GwangSanColor
import com.school_of_company.signup.viewmodel.SignUpViewModel
import com.school_of_company.design_system.component.textfield.GwangSanSelectTextField
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun PlaceNameRoute(
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
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
    val placeName by viewModel.placeName.collectAsStateWithLifecycle()

    PlaceNameScreen(
        onBackClick = onBackClick,
        onNextClick = onNextClick,
        placeName = placeName,
        onPlaceNameChange = viewModel::onPlaceNameChange,
    )
}

@Composable
private fun PlaceNameScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    placeName: String,
    onPlaceNameChange: (String) -> Unit,
) {

    val isDropdownVisible = rememberSaveable { mutableStateOf(false) }

    val onToggleDropdown: () -> Unit = { isDropdownVisible.value = !isDropdownVisible.value }

    val backgroundColor = if (isDropdownVisible.value) GwangSanColor.gray300 else GwangSanColor.white
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    GwangSanTheme { colors, typography ->

        Box(
            modifier = modifier
                .fillMaxSize()
                .background(backgroundColor)
                .pointerInput(isDropdownVisible) {
                    detectTapGestures {
                        if (isDropdownVisible.value) {
                            isDropdownVisible.value = false
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
                        .padding(
                            top = 80.dp,
                            bottom = 32.dp
                        ),
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
                    text = "지점을 선택해주세요",
                    style = typography.label,
                    color = colors.black.copy(alpha = 0.5f)
                )

                Spacer(modifier = Modifier.height(32.dp))

                GwangSanSelectTextField(
                    label = "지점",
                    value = placeName,
                    placeHolder = "지점을 선택해주세요",
                    onClick = onToggleDropdown,
                    onTextChange = { /* NONE */ }
                )

                if (isDropdownVisible.value) {
                    Spacer(modifier = Modifier.height(8.dp))

                    SingleSelectDropdown(
                        options = persistentListOf("고실마을", "수완마을", "신가동", "신창동", "도산동", "우산동", "월곡1동", "첨단2동", "평동", "월곡2동", "하남동"),
                        selectedOption = placeName,
                        onOptionClick = onPlaceNameChange,
                        onDismissRequest = { isDropdownVisible.value = false },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))
            }

            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 24.dp,
                        end = 24.dp,
                        bottom = 64.dp
                    ),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isDropdownVisible.value) colors.gray200 else colors.white)
                ) {
                    if (isDropdownVisible.value) {
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
                            state = if (placeName.isNotBlank()) ButtonState.Enable else ButtonState.Disable,
                            onClick = { onNextClick() },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, name = "PlaceName")
@Composable
private fun PlaceNameScreenPreview_Empty() {
    var place by rememberSaveable { mutableStateOf("") }

    PlaceNameScreen(
        onBackClick = {},
        onNextClick = {},
        placeName = place,
        onPlaceNameChange = { place = it }
    )
}

