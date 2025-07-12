package com.school_of_company.content.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.school_of_company.content.component.ReportBottomSheet
import com.school_of_company.content.viewmodel.ContentViewModel
import com.school_of_company.content.viewmodel.uistate.GetSpecificPostUiState
import com.school_of_company.design_system.R
import com.school_of_company.design_system.componet.button.GwangSanEnableButton
import com.school_of_company.design_system.componet.button.GwangSanStateButton
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.CloseIcon
import com.school_of_company.design_system.componet.icons.DownArrowIcon
import com.school_of_company.design_system.componet.recycle.CleaningRequestCard
import com.school_of_company.design_system.componet.recycle.MyProfileUserLevel
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.ui.previews.GwangsanPreviews

@Composable
internal fun ReadMoreRoute(
    postId: Long,
    onBackClick: () -> Unit,
    onOtherProfileClick: (Long) -> Unit,
    onChatClick: () -> Unit,
    onReviewClick: (Int, String) -> Unit,
    onReportClick: (String, String) -> Unit,
    viewModel: ContentViewModel = hiltViewModel()
) {
    val getSpecificPostUiState by viewModel.getSpecificPostUiState.collectAsStateWithLifecycle()

    LaunchedEffect(postId) {
        viewModel.getSpecificPost(postId = postId)
    }

    ReadMoreScreen(
        getSpecificPostUiState = getSpecificPostUiState,
        onBackClick = onBackClick,
        onOtherProfileClick = onOtherProfileClick,
        onChatClick = onChatClick,
        onReviewClick = onReviewClick,
        onReportClick = onReportClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReadMoreScreen(
    modifier: Modifier = Modifier,
    getSpecificPostUiState: GetSpecificPostUiState,
    onBackClick: () -> Unit,
    onOtherProfileClick: (Long) -> Unit,
    onChatClick: () -> Unit,
    onReviewClick: (Int, String) -> Unit,
    onReportClick: (String, String) -> Unit
) {

    val (openReportBottomSheet, isOpenReportBottomSheet) = rememberSaveable { mutableStateOf(false) }
    val (openReviewBottomSheet, isOpenReviewBottomSheet) = rememberSaveable { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    GwangSanTheme { colors, typography ->

        when (getSpecificPostUiState) {
            is GetSpecificPostUiState.Success -> {
                val pagerState =
                    rememberPagerState(pageCount = { getSpecificPostUiState.post.images.size })

                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .background(colors.white)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                    ) {
                        GwangSanSubTopBar(
                            startIcon = { DownArrowIcon(modifier = Modifier.GwangSanClickable { onBackClick() }) },
                            betweenText = "해주세요",
                            endIcon = { Spacer(modifier = Modifier.size(24.dp)) },
                            modifier = Modifier.padding(
                                top = 52.dp,
                                start = 24.dp,
                                end = 24.dp
                            )
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        if (getSpecificPostUiState.post.images.isEmpty()) {
                            Image(
                                painter = painterResource(id = R.drawable.gwangsan),
                                contentDescription = "기본 이미지",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(280.dp)
                            )
                        } else {
                            HorizontalPager(
                                state = pagerState,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(280.dp)
                            ) { page ->
                                val image = getSpecificPostUiState.post.images[page]
                                AsyncImage(
                                    model = image.imageUrl,
                                    contentDescription = "게시물 이미지 $page",
                                    placeholder = painterResource(id = R.drawable.gwangsan),
                                    error = painterResource(id = R.drawable.gwangsan),
                                    fallback = painterResource(id = R.drawable.gwangsan),
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                        ) {
                            repeat(getSpecificPostUiState.post.images.size) { index ->
                                val isSelected = pagerState.currentPage == index
                                Box(
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp)
                                        .size(if (isSelected) 8.dp else 6.dp)
                                        .background(
                                            color = if (isSelected) colors.main500 else colors.gray300,
                                            shape = androidx.compose.foundation.shape.CircleShape
                                        )
                                )
                            }
                        }

                        MyProfileUserLevel(
                            modifier = Modifier.padding(
                                horizontal = 24.dp,
                                vertical = 12.dp
                            ),
                            onClick =  onOtherProfileClick,
                            data = getSpecificPostUiState.post.member
                        )

                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(color = colors.gray100)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        CleaningRequestCard(
                            title = getSpecificPostUiState.post.title,
                            priceAndLocation = "${getSpecificPostUiState.post.gwangsan} 광산",
                            description = getSpecificPostUiState.post.content,
                            modifier = Modifier.padding(horizontal = 24.dp)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "이 게시글 신고하기",
                            style = typography.label.copy(
                                textDecoration = TextDecoration.Underline
                            ),
                            color = colors.error,
                            modifier = Modifier
                                .GwangSanClickable { isOpenReportBottomSheet(true) }
                                .padding(horizontal = 24.dp)
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = 24.dp,
                                    start = 24.dp,
                                    end = 24.dp,
                                    bottom = 43.dp
                                )
                        ) {
                            GwangSanEnableButton(
                                text = "채팅하기",
                                backgroundColor = colors.white,
                                textColor = colors.main500,
                                onClick = onChatClick,
                                modifier = Modifier
                                    .weight(1f)
                                    .border(
                                        width = 1.dp,
                                        color = colors.main500,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            )

                            GwangSanStateButton(
                                text = "거래완료",
                                onClick = { isOpenReviewBottomSheet(true) },
                                modifier = Modifier
                                    .weight(1f)
                                    .border(
                                        width = 1.dp,
                                        color = colors.main500,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            )
                        }
                    }
                }
            }

            is GetSpecificPostUiState.Loading -> Unit
            is GetSpecificPostUiState.Error -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = colors.white)
                ) {
                    Text(
                        text = "게시물 정보를 가져올 수 없어요..",
                        style = typography.titleMedium2,
                        color = colors.gray500
                    )

                    Spacer(modifier = Modifier.padding(bottom = 8.dp))

                    Text(
                        text = "뒤로가기",
                        style = typography.body3.copy(
                            textDecoration = TextDecoration.Underline
                        ),
                        color = colors.main500
                    )
                }
            }
        }
    }

    if (openReportBottomSheet) {
        Dialog(onDismissRequest = { isOpenReportBottomSheet(false) }) {
            ReportBottomSheet(
                onDismiss = { isOpenReportBottomSheet(false) },
                onSubmit = onReportClick,
            )
        }
    }

    if (openReviewBottomSheet) {
        Dialog(onDismissRequest = { isOpenReviewBottomSheet(false) }) {
            ReviewBottomSheet(
                onDismiss = { isOpenReviewBottomSheet(false) },
                onSubmit = onReviewClick,
            )
        }
    }
}

@GwangsanPreviews
@Composable
private fun PreviewReadMoreScreen() {
    val dummyPost = com.school_of_company.model.post.response.Post(
        id = 1L,
        type = "SERVICE",
        mode = "RECEIVER",
        title = "에어컨 청소 부탁드립니다",
        content = "에어컨 청소가 필요합니다. 더운 여름이라 빠른 시일 내에 부탁드려요.",
        gwangsan = 10000,
        member = com.school_of_company.model.post.response.Member(
            memberId = 10L,
            nickname = "광산이",
            placeName = "광산구 수완동",
            light = 3
        ),
        images = listOf(
            com.school_of_company.model.post.response.Image(
                imageId = 1L,
                imageUrl = "https://via.placeholder.com/600/92c952"
            ),
            com.school_of_company.model.post.response.Image(
                imageId = 2L,
                imageUrl = "https://via.placeholder.com/600/771796"
            ),
            com.school_of_company.model.post.response.Image(
                imageId = 3L,
                imageUrl = "https://via.placeholder.com/600/24f355"
            ),
            com.school_of_company.model.post.response.Image(
                imageId = 4L,
                imageUrl = "https://via.placeholder.com/600/d32776"
            )
        )
    )

    ReadMoreScreen(
        onChatClick = {},
        onBackClick = {},
        onOtherProfileClick = {},
        onReportClick = { _, _ -> },
        onReviewClick = { _, _ -> },
        getSpecificPostUiState = GetSpecificPostUiState.Success(dummyPost)
    )
}