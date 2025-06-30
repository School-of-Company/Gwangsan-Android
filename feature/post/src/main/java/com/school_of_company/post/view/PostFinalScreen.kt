package com.school_of_company.post.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.school_of_company.design_system.R
import com.school_of_company.design_system.component.progress.GwangSanTopBarProgress
import com.school_of_company.design_system.componet.button.GwangSanEnableButton
import com.school_of_company.design_system.componet.button.GwangSanStateButton
import com.school_of_company.design_system.componet.button.state.ButtonState
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icon.AddImageButton
import com.school_of_company.design_system.componet.icons.CloseIcon
import com.school_of_company.design_system.componet.icons.DownArrowIcon
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.design_system.theme.color.GwangSanColor
import com.school_of_company.model.enum.Mode
import com.school_of_company.model.enum.Type
import com.school_of_company.post.viewmodel.PostViewModel
import com.school_of_company.post.viewmodel.uiState.PostUiState

@Composable
internal fun PostFinalRoute(
    type: Type,
    mode: Mode,
    onEditClick: () -> Unit,
    onSubmitClick: () -> Unit,
    onBackClick: () -> Unit,
    onCloseClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    viewModel: PostViewModel = hiltViewModel(),
) {
    val subject by viewModel.title.collectAsState()
    val content by viewModel.content.collectAsState()
    val price by viewModel.gwangsan.collectAsState()
    val postUiState by viewModel.postUiState.collectAsState()

    LaunchedEffect(postUiState) {
        when (postUiState) {
            is PostUiState.Loading -> {
                Log.d("PostFinalRoute", "게시글 작성 중...")
            }
            is PostUiState.Success -> {
                Log.d("PostFinalRoute", "게시글 작성 성공!")
                onSubmitClick()
            }
            is PostUiState.Conflict -> {
                Log.e("PostFinalRoute", "중복된 게시글 요청")
            }
            is PostUiState.BadRequest -> {
                Log.e("PostFinalRoute", "잘못된 요청")
                onErrorToast(null, R.string.error_bad_request)
            }
            is PostUiState.NotFound -> {
                Log.e("PostFinalRoute", "서버에서 리소스를 찾을 수 없음")
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
        imageContent = {
            AddImageButton(
                onClick = {},
                rippleColor = GwangSanColor.main100
            )
        },
        onEditClick = onEditClick,
        onSubmitClick = { viewModel.writePost() },
        onBackClick = onBackClick,
        onCloseClick = onCloseClick
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
    onCloseClick: () -> Unit
) {
    GwangSanTheme { colors, typography ->
        Column(modifier = modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(52.dp))

            GwangSanSubTopBar(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                startIcon = {
                    DownArrowIcon(
                        modifier = Modifier.width(8.dp).height(14.dp).GwangSanClickable(onClick = onBackClick)
                    )
                },
                betweenText = "해주세요",
                endIcon = {
                    CloseIcon(
                        modifier = Modifier.size(24.dp).GwangSanClickable (onClick = onCloseClick)
                    )
                }
            )

            Spacer(modifier = Modifier.height(8.dp))
            GwangSanTopBarProgress(progressRatio = 1.0f, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(32.dp))

            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Text(text = "다시 한번 확인해주세요.", style = typography.titleSmall, color = colors.black)
                Spacer(modifier = Modifier.height(28.dp))

                Text(text = "주제", style = typography.body5, color = colors.black)
                Box(
                    modifier = Modifier.fillMaxWidth().background(colors.white, RoundedCornerShape(8.dp))
                        .border(1.dp, GwangSanColor.subYellow500, RoundedCornerShape(8.dp)).padding(16.dp)
                ) {
                    Text(text = subject, style = typography.body5, color = colors.black)
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "내용", style = typography.body5, color = colors.black)
                Box(
                    modifier = Modifier.fillMaxWidth().background(colors.white, RoundedCornerShape(8.dp))
                        .border(1.dp, GwangSanColor.subYellow500, RoundedCornerShape(8.dp)).padding(16.dp).height(185.dp)
                ) {
                    Text(text = content, style = typography.body5, color = colors.black)
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "사진첨부", style = typography.body5, color = colors.black)
                Spacer(modifier = Modifier.height(12.dp))
                Row { imageContent() }

                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "광산", style = typography.body5, color = colors.black)
                Box(
                    modifier = Modifier.fillMaxWidth().background(colors.white, RoundedCornerShape(8.dp))
                        .border(1.dp, GwangSanColor.subYellow500, RoundedCornerShape(8.dp)).padding(16.dp)
                ) {
                    Text(text = price, style = typography.body5, color = colors.black)
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                    GwangSanEnableButton(
                        text = "수정", textColor = GwangSanColor.main500, backgroundColor = GwangSanColor.white,
                        onClick = onEditClick,
                        modifier = Modifier.weight(1f).height(56.dp)
                            .border(1.dp, GwangSanColor.main500, RoundedCornerShape(8.dp))
                    )

                    GwangSanStateButton(
                        text = "완료", state = ButtonState.Enable, onClick = onSubmitClick,
                        modifier = Modifier.weight(1f).height(56.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}