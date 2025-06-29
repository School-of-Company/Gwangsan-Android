package com.school_of_company.post.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.school_of_company.design_system.component.progress.GwangSanTopBarProgress
import com.school_of_company.design_system.componet.button.GwangSanStateButton
import com.school_of_company.design_system.componet.button.state.ButtonState
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.CloseIcon
import com.school_of_company.design_system.componet.icons.DownArrowIcon
import com.school_of_company.design_system.componet.icon.AddImageButton
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.design_system.theme.color.GwangSanColor
import com.school_of_company.model.enum.Mode
import com.school_of_company.model.enum.Type
import com.school_of_company.post.viewmodel.PostViewModel
import com.yourpackage.design_system.component.textField.GwangSanTextField

@Composable
internal fun PostWriteRoute(
    type: Type,
    mode: Mode,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    viewModel: PostViewModel = hiltViewModel(),
) {
    val subject by viewModel.title.collectAsState()
    val content by viewModel.content.collectAsState()

    PostWriteScreen(
        subject = subject,
        content = content,
        onSubjectChange = viewModel::onTitleChange,
        onContentChange = viewModel::onContentChange,
        onImageAdd = {},
        onNextClick = onNextClick,
        onBackClick = onBackClick,
        imageContent = { isEnabled ->
            AddImageButton(
                onClick = {},
                rippleColor = if (isEnabled) GwangSanColor.main100 else GwangSanColor.gray300
            )
        }
    )
}

@Composable
private fun PostWriteScreen(
    modifier: Modifier = Modifier,
    subject: String,
    content: String,
    onSubjectChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onImageAdd: () -> Unit,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
    imageContent: @Composable (Boolean) -> Unit
) {
    val isNextEnabled = subject.isNotBlank() && content.isNotBlank()

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
                            .GwangSanClickable(onClick = onBackClick)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
            )

            Spacer(modifier = Modifier.height(8.dp))

            GwangSanTopBarProgress(
                progressRatio = 0.3f,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 0.dp),
            )

            Spacer(modifier = Modifier.height(32.dp))

            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                GwangSanTextField(
                    value = subject,
                    onTextChange = onSubjectChange,
                    label = "주제",
                    placeHolder = "주제를 작성해주세요"
                )

                Spacer(modifier = Modifier.height(28.dp))

                GwangSanTextField(
                    value = content,
                    onTextChange = onContentChange,
                    label = "내용",
                    placeHolder = "내용을 작성해주세요",
                    singleLine = false,
                    maxLines = Int.MAX_VALUE,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(185.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "사진첨부",
                    style = typography.body5,
                    color = colors.black
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    imageContent(isNextEnabled)
                }

                Spacer(modifier = Modifier.weight(1f, fill = true))

                GwangSanStateButton(
                    text = "다음",
                    state = if (isNextEnabled) ButtonState.Enable else ButtonState.Disable,
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