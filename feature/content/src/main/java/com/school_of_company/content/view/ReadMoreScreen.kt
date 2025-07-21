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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.school_of_company.content.component.ReportBottomSheet
import com.school_of_company.content.viewmodel.ContentViewModel
import com.school_of_company.content.viewmodel.uistate.DeletePostUiState
import com.school_of_company.content.viewmodel.uistate.GetSpecificPostUiState
import com.school_of_company.content.viewmodel.uistate.ReportPostUiState
import com.school_of_company.content.viewmodel.uistate.ReviewPostUiState
import com.school_of_company.content.viewmodel.uistate.TransactionCompleteUiState
import com.school_of_company.design_system.R
import com.school_of_company.design_system.component.button.GwangSanEnableButton
import com.school_of_company.design_system.component.button.GwangSanStateButton
import com.school_of_company.design_system.component.button.state.ButtonState
import com.school_of_company.design_system.component.clickable.GwangSanClickable
import com.school_of_company.design_system.component.dialog.GwangsanDialog
import com.school_of_company.design_system.component.icons.DownArrowIcon
import com.school_of_company.design_system.component.recycle.CleaningRequestCard
import com.school_of_company.design_system.component.recycle.MyProfileUserLevel
import com.school_of_company.design_system.component.toast.makeToast
import com.school_of_company.design_system.component.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.model.post.request.TransactionCompleteRequestModel
import com.school_of_company.model.report.request.ReportRequestModel
import com.school_of_company.model.review.request.ReviewRequestModel

@Composable
internal fun ReadMoreRoute(
    postId: Long,
    onBackClick: () -> Unit,
    onOtherProfileClick: (Long) -> Unit,
    onChatClick: () -> Unit,
    onReviewClick: (Int, String) -> Unit,
    onReportClick: (String, String) -> Unit,
    onEditClick: (Long, String, String) -> Unit,
    viewModel: ContentViewModel = hiltViewModel()
) {
    val getSpecificPostUiState by viewModel.getSpecificPostUiState.collectAsStateWithLifecycle()
    val reportPostUiState by viewModel.reportPostUiState.collectAsStateWithLifecycle()
    val reviewPostUiState by viewModel.reviewPostUiState.collectAsStateWithLifecycle()
    val transactionCompleteUiState by viewModel.transactionCompleteUiState.collectAsStateWithLifecycle()
    val deletePostUiState by viewModel.deletePostUiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val (openReportBottomSheet, setOpenReportBottomSheet) = rememberSaveable { mutableStateOf(false) }
    val (openReviewBottomSheet, setOpenReviewBottomSheet) = rememberSaveable { mutableStateOf(false) }
    val (openDeleteBottomSheet, setOpenDeleteBottomSheet) = rememberSaveable { mutableStateOf(false) }

    val (buttonText, setButtonText) = remember { mutableStateOf("거래완료") }

    val isPostTradeState = transactionCompleteUiState is TransactionCompleteUiState.Success

    LaunchedEffect(postId) {
        viewModel.getSpecificPost(postId)
    }

    LaunchedEffect(deletePostUiState) {
        when (deletePostUiState) {
            is DeletePostUiState.Loading -> Unit
            is DeletePostUiState.Success -> {
                makeToast(context, "내 게시물 삭제 성공")
                setOpenDeleteBottomSheet(false)
                onBackClick()
            }
            is DeletePostUiState.Error -> {
                makeToast(context, "내 게시물 삭제 실패")
            }
        }
    }

    LaunchedEffect(reportPostUiState) {
        when (reportPostUiState) {
            is ReportPostUiState.Loading -> Unit
            is ReportPostUiState.Success -> {
                makeToast(context, "신고 성공")
                setOpenReportBottomSheet(false)
            }

            is ReportPostUiState.Error -> {
                makeToast(context, "신고 실패")
            }
        }
    }

    LaunchedEffect(reviewPostUiState) {
        when (reviewPostUiState) {
            is ReviewPostUiState.Loading -> Unit
            is ReviewPostUiState.Success -> {
                makeToast(context, "리뷰를 성공하였습니다.")
                setOpenReviewBottomSheet(false)
            }

            is ReviewPostUiState.Error -> {
                makeToast(context, "리뷰를 실패하였습니다.")
            }
        }
    }

    LaunchedEffect(transactionCompleteUiState) {
        when (transactionCompleteUiState) {
            is TransactionCompleteUiState.Loading -> Unit
            is TransactionCompleteUiState.Success -> {
                setButtonText("리뷰쓰기")
                setOpenReviewBottomSheet(false)
                makeToast(context, "거래완료 성공")
            }

            is TransactionCompleteUiState.Error -> {
                makeToast(context, "거래완료 실패")
            }
        }
    }

    when (getSpecificPostUiState) {

        is GetSpecificPostUiState.Success -> {
            val post = (getSpecificPostUiState as GetSpecificPostUiState.Success).post
            ReadMoreScreen(
                getSpecificPostUiState = getSpecificPostUiState,
                buttonText = buttonText,
                onBackClick = onBackClick,
                onEditClick = {
                    onEditClick(post.id, post.type, post.mode)
                },
                onOtherProfileClick = onOtherProfileClick,
                onChatClick = onChatClick,
                onReviewClick = onReviewClick,
                onReportClick = onReportClick,
                onTransactionCompleteCallBack = {
                    viewModel.transactionComplete(
                        body = TransactionCompleteRequestModel(
                            productId = postId,
                            otherMemberId = (getSpecificPostUiState as GetSpecificPostUiState.Success).post.member.memberId
                        )
                    )
                },
                onReviewCallBack = { light, content ->
                    viewModel.reviewPost(
                        body = ReviewRequestModel(
                            productId = postId,
                            content = content,
                            light = light
                        )
                    )
                },
                onReportCallBack = { reportType, reportContent ->
                    viewModel.reportPost(
                        body = ReportRequestModel(
                            productId = postId,
                            reportType = reportType,
                            content = reportContent
                        )
                    )
                },
                onDeleteCallBack = { viewModel.deletePost(postId = postId) },
                openReportBottomSheet = openReportBottomSheet,
                openReviewBottomSheet = openReviewBottomSheet,
                openDeleteBottomSheet = openDeleteBottomSheet,
                setOpenReportBottomSheet = setOpenReportBottomSheet,
                setOpenReviewBottomSheet = setOpenReviewBottomSheet,
                setOpenDeleteBottomSheet = setOpenDeleteBottomSheet,
                isPostTradeState = isPostTradeState
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReadMoreScreen(
    modifier: Modifier = Modifier,
    isPostTradeState: Boolean = false,
    getSpecificPostUiState: GetSpecificPostUiState,
    buttonText: String,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onOtherProfileClick: (Long) -> Unit,
    onChatClick: () -> Unit,
    onReviewClick: (Int, String) -> Unit,
    onReportClick: (String, String) -> Unit,
    onTransactionCompleteCallBack: () -> Unit,
    onReportCallBack: (String, String) -> Unit,
    onReviewCallBack: (Int, String) -> Unit,
    onDeleteCallBack: () -> Unit,
    openReportBottomSheet: Boolean,
    openReviewBottomSheet: Boolean,
    openDeleteBottomSheet: Boolean,
    setOpenReportBottomSheet: (Boolean) -> Unit,
    setOpenReviewBottomSheet: (Boolean) -> Unit,
    setOpenDeleteBottomSheet: (Boolean) -> Unit
) {
    val scrollState = rememberScrollState()

    GwangSanTheme { colors, typography ->

        when (getSpecificPostUiState) {
            is GetSpecificPostUiState.Success -> {
                val post = getSpecificPostUiState.post
                val pagerState = rememberPagerState(pageCount = { post.images.size })

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
                            startIcon = {
                                DownArrowIcon(modifier = Modifier.GwangSanClickable { onBackClick() })
                            },
                            betweenText = "해주세요",
                            endIcon = { Spacer(modifier = Modifier.size(24.dp)) },
                            modifier = Modifier.padding(
                                top = 52.dp,
                                start = 24.dp,
                                end = 24.dp
                            )
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        if (post.images.isEmpty()) {
                            Image(
                                painter = painterResource(id = R.drawable.gwangsan),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(280.dp),
                            )
                        } else {
                            HorizontalPager(
                                state = pagerState,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(280.dp)
                            ) { page ->
                                AsyncImage(
                                    model = post.images[page].imageUrl,
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop,
                                    placeholder = painterResource(id = R.drawable.gwangsan),
                                    error = painterResource(id = R.drawable.gwangsan),
                                    fallback = painterResource(id = R.drawable.gwangsan)
                                )
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        ) {
                            repeat(post.images.size) { index ->
                                val isSelected = pagerState.currentPage == index
                                Box(
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp)
                                        .size(if (isSelected) 8.dp else 6.dp)
                                        .background(
                                            color = if (isSelected) colors.main500 else colors.gray300,
                                            shape = RoundedCornerShape(50)
                                        )
                                )
                            }
                        }

                        MyProfileUserLevel(
                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                            onClick = onOtherProfileClick,
                            data = post.member
                        )

                        Spacer(
                            modifier = Modifier
                                .height(1.dp)
                                .fillMaxWidth()
                                .background(colors.gray100)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        CleaningRequestCard(
                            title = post.title,
                            priceAndLocation = "${post.gwangsan} 광산",
                            description = post.content,
                            modifier = Modifier.padding(horizontal = 24.dp)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = if (getSpecificPostUiState.post.isMine) "이 게시글 삭제하기" else "이 게시글 신고하기",
                            style = typography.label.copy(textDecoration = TextDecoration.Underline),
                            color = colors.error,
                            modifier = Modifier
                                .GwangSanClickable {
                                    if (getSpecificPostUiState.post.isMine) setOpenDeleteBottomSheet(true) else setOpenReportBottomSheet(true)
                                }
                                .padding(horizontal = 24.dp)
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp)
                        ) {
                            GwangSanEnableButton(
                                text = if (getSpecificPostUiState.post.isMine) "수정" else "채팅하기",
                                backgroundColor = colors.white,
                                textColor = colors.main500,
                                onClick = if (getSpecificPostUiState.post.isMine) onEditClick else onChatClick,
                                modifier = Modifier
                                    .weight(1f)
                                    .border(1.dp, colors.main500, RoundedCornerShape(8.dp))
                            )

                            if (isPostTradeState) {
                                GwangSanStateButton(
                                    text = "리뷰쓰기",
                                    onClick = {
                                        setOpenReviewBottomSheet(true)
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .border(1.dp, colors.main500, RoundedCornerShape(8.dp))
                                )
                            } else {
                                GwangSanStateButton(
                                    text = "거래하기",
                                    state = when {
                                        getSpecificPostUiState.post.isCompleted -> ButtonState.Disable
                                        getSpecificPostUiState.post.isCompletable -> ButtonState.Enable
                                        else -> ButtonState.Disable
                                    },
                                    onClick = { onTransactionCompleteCallBack() },
                                    modifier = Modifier
                                        .weight(1f)
                                        .border(1.dp, colors.main500, RoundedCornerShape(8.dp))
                                )
                            }
                        }

                        Spacer(modifier = Modifier.padding(bottom = 40.dp))
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

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "뒤로가기",
                        style = typography.body3.copy(textDecoration = TextDecoration.Underline),
                        color = colors.main500,
                        modifier = Modifier.GwangSanClickable { onBackClick() }
                    )
                }
            }
        }
    }

    if (openReportBottomSheet) {
        Dialog(onDismissRequest = { setOpenReportBottomSheet(false) }) {
            ReportBottomSheet(
                onDismiss = { setOpenReportBottomSheet(false) },
                onSubmit = { reportType, reportContent ->
                    onReportCallBack(reportType, reportContent)
                }
            )
        }
    }

    if (openReviewBottomSheet) {
        Dialog(onDismissRequest = { setOpenReviewBottomSheet(false) }) {
            ReviewBottomSheet(
                onDismiss = { setOpenReviewBottomSheet(false) },
                onSubmit = { content, light ->
                    onReviewCallBack(content, light)
                }
            )
        }
    }

    if (openDeleteBottomSheet) {
        Dialog(onDismissRequest = { setOpenDeleteBottomSheet(false) }) {
            GwangsanDialog(
                onDismiss = { setOpenDeleteBottomSheet(false) },
                onLogout = { onDeleteCallBack() },
                titleText = "게시글 삭제",
                contentText = "이 게시글을 삭제하시겠습니까?",
                buttonText = "삭제"
            )
        }
    }
}

@Preview
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
        ),
        isCompletable = true,
        isCompleted = false,
        isMine = false
    )

    ReadMoreScreen(
        onChatClick = {},
        onBackClick = {},
        onOtherProfileClick = {},
        onReportClick = { _, _ -> },
        onReviewClick = { _, _ -> },
        getSpecificPostUiState = GetSpecificPostUiState.Success(dummyPost),
        onReportCallBack = { _, _ -> },
        setOpenReportBottomSheet = {},
        setOpenReviewBottomSheet = {},
        openReportBottomSheet = false,
        openReviewBottomSheet = false,
        onReviewCallBack = { _, _ -> },
        onTransactionCompleteCallBack = {},
        isPostTradeState = true,
        buttonText = "",
        openDeleteBottomSheet = false,
        setOpenDeleteBottomSheet = {},
        onDeleteCallBack = {},
        onEditClick = {}
    )
}