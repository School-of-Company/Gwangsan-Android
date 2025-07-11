package com.school_of_company.post.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
import com.school_of_company.ui.previews.GwangsanPreviews
import com.yourpackage.design_system.component.textField.GwangSanTextField

@Composable
internal fun PostWriteRoute(
    type: Type,
    mode: Mode,
    onBackClick: () -> Unit,
    onNextClick: (String, String) -> Unit,
    viewModel: PostViewModel? = null,
) {
    val actualViewModel = viewModel ?: hiltViewModel()
    val subject by actualViewModel.title.collectAsState()
    val content by actualViewModel.content.collectAsState()

    PostWriteScreen(
        subject = subject,
        content = content,
        onSubjectChange = actualViewModel::onTitleChange,
        onContentChange = actualViewModel::onContentChange,
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
    onNextClick: (String, String) -> Unit,
    onBackClick: () -> Unit,
    imageContent: @Composable (Boolean) -> Unit
) {
    val isNextEnabled = subject.isNotBlank() && content.isNotBlank()

    GwangSanTheme { colors, typography ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colors.white)
                .padding(horizontal = 24.dp)
        ) {
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
                onClick = { onNextClick(subject, content) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )

            Spacer(modifier = Modifier.height(64.dp))
        }
    }

}

@GwangsanPreviews
@Composable
private fun PostWritePreview() {
    PostWriteScreen(
        subject = "예시 주제",
        content = "예시 내용입니다.\n여러 줄로 작성된 내용도 미리보기 됩니다.",
        onSubjectChange = {},
        onContentChange = {},
        onImageAdd = {},
        onNextClick = { _, _ -> },
        onBackClick = {},
        imageContent = { isEnabled ->
            AddImageButton(
                onClick = {},
                rippleColor = if (isEnabled) GwangSanColor.main100 else GwangSanColor.gray300
            )
        }
    )
}
