package com.school_of_company.signup.view

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.componet.button.GwangSanStateButton
import com.school_of_company.design_system.componet.button.GwangSanButton
import com.school_of_company.design_system.componet.button.state.ButtonState
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.DownArrowIcon
import com.school_of_company.design_system.componet.icons.PlusIcon
import com.school_of_company.design_system.componet.topbar.GwangSanTopBar
import com.school_of_company.design_system.theme.color.GwangSanColor
import com.yourpackage.design_system.component.textField.GwangSanTextField
import com.school_of_company.design_system.theme.GwangSanTheme
@Composable
internal fun IntroduceRoute(
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
){
    IntroduceScreen(
        onBackClick = onBackClick,
        onNextClick = onNextClick,
        introduce = emptyList(),
        onIntroduceChange = {},
        buttonEnabled = false,
        isDropdownVisible = false,
        onDismissRequest = {},
        showPlusIcon = true,
        isFocused = false,
    )

}

@Composable
private fun IntroduceScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    focusManager: androidx.compose.ui.focus.FocusManager = LocalFocusManager.current,
    scrollState: ScrollState = rememberScrollState(),
    introduce: List<String>,
    onIntroduceChange: (List<String>) -> Unit,
    onNextClick: () -> Unit,
    showPlusIcon: Boolean = true,
    isFocused: Boolean = false,
    buttonEnabled: Boolean = false,
    isDropdownVisible: Boolean = false,
    onDismissRequest: () -> Unit = {}
) {
    val options = listOf("청소하기", "운전하기", "달리기", "빨래하기", "벌레잡기", "이삿짐 나르기")

    GwangSanTheme { colors, typography ->

        val backgroundColor = if (isDropdownVisible) GwangSanColor.gray300 else colors.white

        Box(
            modifier = modifier
                .fillMaxSize()
                .background(colors.background)
                .background(backgroundColor)
                .imePadding()
                .pointerInput(Unit) {
                    detectTapGestures { focusManager.clearFocus() }
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
                        startIcon = { DownArrowIcon(modifier = Modifier.GwangSanClickable {onBackClick() }) },
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

                if (isDropdownVisible) {
                    Spacer(modifier = Modifier.height(6.dp))

                    Column(modifier = Modifier.fillMaxWidth())
                    {
                        Box(modifier = Modifier.fillMaxWidth())
                        {
                            GwangSanTextField(
                                label = "소개",
                                value = introduce.joinToString(", "),
                                placeHolder = "소개 추가",
                                onTextChange = {},
                                icon = { if (showPlusIcon) PlusIcon() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .GwangSanClickable { onIntroduceChange(introduce) }
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        MultiSelectDropdown(
                            options = options,
                            selectedOptions = introduce,
                            onSelectionChange = onIntroduceChange,
                            onDismissRequest = onDismissRequest,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                } else {
                    GwangSanTextField(
                        label = "소개",
                        value = introduce.joinToString(", "),
                        placeHolder = "소개 추가",
                        onTextChange = {},
                        icon = { if (showPlusIcon) PlusIcon() },
                        isReadOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .GwangSanClickable { onIntroduceChange(introduce) }
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
                        .background(
                            if (isDropdownVisible) colors.gray200 else colors.background
                        )
                ) {
                    if (isDropdownVisible) {
                        GwangSanButton(
                            text = "다음",
                            color = colors.gray400,
                            textColor = colors.gray500,
                            onClick = {},
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                        )
                    } else {
                        GwangSanStateButton(
                            text = "다음",
                            state = if (buttonEnabled) ButtonState.Enable else ButtonState.Disable,
                            onClick = onNextClick,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IntroduceScreenPreview1() {
    IntroduceScreen(
        introduce = listOf("빨래하기", "벌레잡기"),
        onIntroduceChange = {},
        onNextClick = {},
        isDropdownVisible = true,
        buttonEnabled = false,
        onDismissRequest = {},
        showPlusIcon = false,
        isFocused = true,
        focusManager = LocalFocusManager.current,
        scrollState = rememberScrollState(),
        modifier = Modifier.background(GwangSanColor.white),
        onBackClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun IntroduceScreenPreview2() {
    IntroduceScreen(
        introduce = listOf("빨래하기", "벌레잡기"),
        onIntroduceChange = {},
        onNextClick = {},
        isDropdownVisible = true,
        buttonEnabled = false,
        onDismissRequest = {},
        showPlusIcon = false,
        isFocused = true,
        focusManager = LocalFocusManager.current,
        scrollState = rememberScrollState(),
        modifier = Modifier.background(GwangSanColor.white),
        onBackClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun IntroduceScreenPreview3() {
    IntroduceScreen(
        introduce = listOf("빨래하기", "벌레잡기"),
        onIntroduceChange = {},
        onNextClick = {},
        isDropdownVisible = true,
        buttonEnabled = false,
        onDismissRequest = {},
        showPlusIcon = false,
        isFocused = true,
        focusManager = LocalFocusManager.current,
        scrollState = rememberScrollState(),
        modifier = Modifier.background(GwangSanColor.white),
        onBackClick = {}
    )
}
