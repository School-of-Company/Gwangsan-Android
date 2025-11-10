package com.school_of_company.post.view

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.school_of_company.design_system.R
import com.school_of_company.design_system.component.button.GwangSanEnableButton
import com.school_of_company.design_system.component.button.GwangSanStateButton
import com.school_of_company.design_system.component.button.state.ButtonState
import com.school_of_company.design_system.component.icons.PlussIcon
import com.school_of_company.design_system.component.toast.makeToast
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.design_system.theme.color.GwangSanColor
import com.school_of_company.model.enum.Mode
import com.school_of_company.model.enum.Type
import com.school_of_company.post.viewmodel.PostViewModel
import com.school_of_company.post.viewmodel.uiState.ModifyPostUiState
import com.school_of_company.post.viewmodel.uiState.PostUiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Composable
internal fun PostFinalRoute(
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

    val selectedType by actualViewModel.type.collectAsState()
    val selectedMode by actualViewModel.mode.collectAsState()

    val existingImageUrls by actualViewModel.existingImageUrls.collectAsState()

    val selectedImageUris by actualViewModel.selectedImages.collectAsStateWithLifecycle()

    val images = (existingImageUrls + selectedImageUris.map { it.toString() }).toPersistentList()

    val postUiState by actualViewModel.postUiState.collectAsState()
    val modifyPostUiState by actualViewModel.modifyPostUiStat.collectAsStateWithLifecycle()
    val isEditMode by actualViewModel.isEditMode.collectAsState()
    val editPostId by actualViewModel.editPostId.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(postUiState) {
        when (postUiState) {
            is PostUiState.Loading -> Unit
            is PostUiState.Success -> {
                makeToast(context, "게시를 성공하였습니다.")
                actualViewModel.resetPostState()
                onSubmitClick()
            }
            is PostUiState.BadRequest -> onErrorToast(null, R.string.error_bad_request)
            is PostUiState.NotFound -> onErrorToast(null, R.string.error_resource_not_found)
            is PostUiState.Error -> onErrorToast((postUiState as PostUiState.Error).exception, R.string.error_generic)
            is PostUiState.NotFoundImage -> onErrorToast(null, R.string.require_image)
            else -> Unit
        }
    }

    LaunchedEffect(modifyPostUiState) {
        when (modifyPostUiState) {
            is ModifyPostUiState.Loading -> Unit
            is ModifyPostUiState.Success -> {
                makeToast(context, "게시글 수정 성공")
                actualViewModel.resetModifyState()
                onSubmitClick()
            }
            is ModifyPostUiState.Error -> {
                makeToast(context, "게시글 수정 실패")
            }
            is ModifyPostUiState.NotFoundImage -> onErrorToast(null, R.string.require_image)
            else -> Unit
        }
    }

    val typeLabel = when (selectedType) {
        Type.OBJECT -> "물건"
        Type.SERVICE -> "서비스"
    }
    val modeLabel = when (selectedMode) {
        Mode.RECEIVER -> "필요해요"
        Mode.GIVER -> "팔아요"
    }

    PostFinalScreen(
        context = context,
        subject = subject,
        content = content,
        price = price,
        images = images,
        onEditClick = onEditClick,
        onSubmitClick = {
            if (isEditMode && editPostId != null) {
                actualViewModel.modifyPost(editPostId!!)
            } else {
                actualViewModel.writePost()
            }
        },
        onBackClick = onBackClick,
        typeLabel = typeLabel,
        modeLabel = modeLabel
    )
}

@Composable
private fun PostFinalScreen(
    modifier: Modifier = Modifier,
    context: Context,
    subject: String,
    content: String,
    price: String,
    images: PersistentList<String>,
    onEditClick: () -> Unit,
    onSubmitClick: () -> Unit,
    onBackClick: () -> Unit,
    typeLabel: String,
    modeLabel: String,
) {
    val scrollState = rememberScrollState()

    GwangSanTheme { colors, typography ->
        Box(modifier = modifier.fillMaxSize()) {

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
                    text = "카테고리",
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
                        text = typeLabel,
                        style = typography.body5,
                        color = colors.black
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "유형",
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
                    Text(text = modeLabel, style = typography.body5, color = colors.black)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "주제", style = typography.body5, color = colors.black)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colors.white, RoundedCornerShape(8.dp))
                        .border(1.dp, GwangSanColor.subYellow500, RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Text(text = subject, style = typography.body5, color = colors.black)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "내용", style = typography.body5, color = colors.black)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colors.white, RoundedCornerShape(8.dp))
                        .border(1.dp, GwangSanColor.subYellow500, RoundedCornerShape(8.dp))
                        .padding(16.dp)
                        .height(185.dp)
                ) {
                    Text(text = content, style = typography.body5, color = colors.black)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "사진첨부",
                    style = typography.body5,
                    color = colors.black
                )


                Spacer(modifier = Modifier.height(12.dp))

                if (images.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFF5F6F8))
                    ) {
                        PlussIcon(
                            tint = colors.black,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                } else {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(images) { uri ->
                            AsyncImage(
                                model = uri,
                                contentDescription = "선택된 이미지",
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
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
                            .border(1.dp,
                                GwangSanColor.main500,
                                RoundedCornerShape(8.dp)
                            )
                    )

                    GwangSanStateButton(
                        text = "완료",
                        state = ButtonState.Enable,
                        onClick = {
                            if (modeLabel == "팔아요" && typeLabel == "물건" && images.isEmpty()) {
                                makeToast(context, "이미지를 첨부해주세요.")
                            } else {
                                onSubmitClick()
                            }
                        },
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

@Preview
@Composable
private fun PostFinalPreview() {
    PostFinalScreen(
        subject = "예시 주제입니다.",
        content = "이곳은 게시글의 본문 내용을 미리 볼 수 있는 영역입니다.\n줄바꿈도 가능합니다.",
        price = "1,000",
        onEditClick = {},
        onSubmitClick = {},
        onBackClick = {},
        images = persistentListOf(),
        typeLabel = "물건",
        modeLabel = "필요해요",
        context = LocalContext.current
    )
}
