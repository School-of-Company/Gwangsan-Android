package com.school_of_company.post.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.school_of_company.design_system.R
import com.school_of_company.design_system.component.progress.GwangSanTopBarProgress
import com.school_of_company.design_system.componet.button.GwangSanEnableButton
import com.school_of_company.design_system.componet.button.GwangSanStateButton
import com.school_of_company.design_system.componet.button.state.ButtonState
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icon.AddImageButton
import com.school_of_company.design_system.componet.icons.DownArrowIcon
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.design_system.theme.color.GwangSanColor
import com.school_of_company.model.enum.Mode
import com.school_of_company.model.enum.Type
import com.school_of_company.post.viewmodel.PostViewModel
import com.school_of_company.post.viewmodel.uiState.PostUiState
import com.school_of_company.ui.previews.GwangsanPreviews

@Composable
internal fun PostFinalRoute(
    type: Type,
    mode: Mode,
    onEditClick: () -> Unit,
    onSubmitClick: () -> Unit,
    onBackClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    viewModel: PostViewModel? = null,
) {
    val actualViewModel = viewModel ?: hiltViewModel()
    val subject by actualViewModel.title.collectAsState()
    val content by actualViewModel.content.collectAsState()
    val price by actualViewModel.gwangsan.collectAsState()
    val postUiState by actualViewModel.postUiState.collectAsState()

    LaunchedEffect(postUiState) {
        when (postUiState) {
            is PostUiState.Loading -> {
                Log.d("PostFinalRoute", "게시글 작성 중...")
            }

            is PostUiState.Success -> {
                onErrorToast(null, R.string.success_post)
                onSubmitClick()
            }

            is PostUiState.BadRequest -> {
                onErrorToast(null, R.string.error_bad_request)
            }

            is PostUiState.NotFound -> {
                onErrorToast(null, R.string.error_resource_not_found)
            }

            is PostUiState.Error -> {
                onErrorToast(
                    (postUiState as PostUiState.Error).exception, R.string.error_generic
                )
            }
        }
    }

    PostFinalScreen(
        subject = subject,
        content = content,
        price = price,
        imageContent = {},
        onEditClick = onEditClick,
        onSubmitClick = { actualViewModel.writePost() },
        onBackClick = onBackClick,
    )
}

@Composable
private fun PostFinalScreen(
    modifier: Modifier = Modifier,
    subject: String,
    content: String,
    price: String,
    imageContent: @Composable () -> Unit,
    onEditClick: () -> Unit,
    onSubmitClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    val scrollState = rememberScrollState()

    GwangSanTheme { colors, typography ->

        Box(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colors.white)
                    .verticalScroll(scrollState)
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    text = "다시 한번 확인해주세요.",
                    style = typography.titleSmall,
                    color = colors.black
                )

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = "주제",
                    style = typography.body5,
                    color = colors.black
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            colors.white,
                            RoundedCornerShape(8.dp)
                        )
                        .border(
                            1.dp, GwangSanColor.subYellow500,
                            RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp)
                ) {
                    Text(
                        text = subject,
                        style = typography.body5,
                        color = colors.black
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "내용",
                    style = typography.body5,
                    color = colors.black
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            colors.white,
                            RoundedCornerShape(8.dp)
                        )
                        .border(
                            1.dp,
                            GwangSanColor.subYellow500,
                            RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp)
                        .height(185.dp)
                ) {
                    Text(
                        text = content,
                        style = typography.body5,
                        color = colors.black
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "사진첨부",
                    style = typography.body5,
                    color = colors.black
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row {
                    AddImageButton(
                        onClick = { },
                        rippleColor = GwangSanColor.main100
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "광산",
                    style = typography.body5,
                    color = colors.black
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colors.white, RoundedCornerShape(8.dp))
                        .border(1.dp, GwangSanColor.subYellow500, RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Text(
                        text = price,
                        style = typography.body5,
                        color = colors.black
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp)
                ) {
                    GwangSanEnableButton(
                        text = "수정",
                        textColor = GwangSanColor.main500,
                        backgroundColor = GwangSanColor.white,
                        onClick = onEditClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .border(
                                1.dp,
                                GwangSanColor.main500,
                                RoundedCornerShape(8.dp)
                            )
                    )

                    GwangSanStateButton(
                        text = "완료",
                        state = ButtonState.Enable,
                        onClick = onSubmitClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                    )
                }

                Spacer(modifier = Modifier.height(64.dp))
            }
        }
    }
}

@GwangsanPreviews
@Composable
private fun PostFinalPreview() {
    PostFinalScreen(
        subject = "예시 주제입니다.",
        content = "이곳은 게시글의 본문 내용을 미리 볼 수 있는 영역입니다.\n줄바꿈도 가능합니다.",
        price = "1,000",
        imageContent = {
            AddImageButton(
                onClick = {},
                rippleColor = GwangSanColor.main100
            )
        },
        onEditClick = {},
        onSubmitClick = {},
        onBackClick = {}
    )
}
