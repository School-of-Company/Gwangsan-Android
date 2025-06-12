package com.school_of_company.post.view.service

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.progress.GwangSanTopBarProgress
import com.school_of_company.design_system.componet.button.GwangSanStateButton
import com.school_of_company.design_system.componet.button.state.ButtonState
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.CloseIcon
import com.school_of_company.design_system.componet.icons.DownArrowIcon
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.yourpackage.design_system.component.textField.GwangSanTextField


@Composable
private fun PostInputScreen(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
    onCloseClick: () -> Unit
) {
    GwangSanTheme { colors, typography ->

        Column(
            modifier = modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(52.dp))

            GwangSanSubTopBar(
                startIcon = {
                    DownArrowIcon(
                        modifier = Modifier
                            .width(8.dp)
                            .height(14.dp)
                            .GwangSanClickable(onClick = onBackClick)
                    )
                },
                betweenText = "해주세요",
                endIcon = {
                    CloseIcon(
                        modifier = Modifier
                            .size(24.dp)
                            .GwangSanClickable(onClick = onCloseClick)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            GwangSanTopBarProgress(
                progressRatio = 0.6f,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                GwangSanTextField(
                    value = value,
                    onTextChange = onValueChange,
                    label = "광산",
                    placeHolder = "광산을 입력해주세요",
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(),
                )

                Spacer(modifier = Modifier.weight(1f))

                GwangSanStateButton(
                    text = "다음",
                    state = if (value.isNotBlank()) ButtonState.Enable else ButtonState.Disable,
                    onClick = onNextClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Preview(showBackground = true, name = "비활성화 상태")
@Composable
fun PreviewPostInputScreenDisabled() {
    var text by remember { mutableStateOf("") }

    PostInputScreen(
        value = text,
        onValueChange = { text = it },
        onNextClick = { println("다음 클릭됨") },
        onBackClick = { println("뒤로가기 클릭됨") },
        onCloseClick = { println("닫기 클릭됨") }
    )
}

@Preview(showBackground = true, name = "활성화 상태")
@Composable
fun PreviewRePostInputEnabled() {
    var text by remember { mutableStateOf("4000") }

    PostInputScreen(
        value = text,
        onValueChange = { text = it },
        onNextClick = { println("다음 클릭됨") },
        onBackClick = { println("뒤로가기 클릭됨") },
        onCloseClick = { println("닫기 클릭됨") }
    )
}