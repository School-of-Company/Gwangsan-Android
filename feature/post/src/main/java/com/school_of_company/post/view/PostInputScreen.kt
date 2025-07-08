package com.school_of_company.post.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.model.enum.Mode
import com.school_of_company.model.enum.Type
import com.school_of_company.post.viewmodel.PostViewModel
import com.school_of_company.ui.previews.GwangsanPreviews
import com.yourpackage.design_system.component.textField.GwangSanTextField

@Composable
internal fun PostInputRoute(
    type: Type,
    mode: Mode,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    viewModel: PostViewModel? = null,
) {
    val actualViewModel = viewModel ?: hiltViewModel()
    val gwangsan by actualViewModel.gwangsan.collectAsState()

    PostInputScreen(
        value = gwangsan,
        onValueChange = actualViewModel::onGwangsanChange,
        onNextClick = onNextClick,
        onBackClick = onBackClick,
    )
}

@Composable
private fun PostInputScreen(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
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
                    modifier = Modifier.fillMaxWidth()
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

@GwangsanPreviews
@Composable
private fun PostInputPreview() {
    PostInputScreen(
        value = "1000",
        onValueChange = {},
        onNextClick = {},
        onBackClick = {},
    )
}
