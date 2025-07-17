package com.school_of_company.profile.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.school_of_company.design_system.componet.button.GwangSanEnableButton
import com.school_of_company.design_system.componet.button.GwangSanStateButton
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.DownArrowIcon
import com.school_of_company.design_system.componet.recycle.CleaningRequestCard
import com.school_of_company.design_system.componet.toast.makeToast
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.model.post.request.TransactionCompleteRequestModel
import com.school_of_company.model.post.response.Post
import com.school_of_company.profile.component.ProfileDialog
import com.school_of_company.profile.viewmodel.MyProfileViewModel
import com.school_of_company.profile.viewmodel.uistate.DeletePostUiState
import com.school_of_company.profile.viewmodel.uistate.GetMySpecificInformationUiState
import com.school_of_company.profile.viewmodel.uistate.TransactionCompleteUiState

@Composable
internal fun ReviewPostDetailRoute(
    onBackClick: () -> Unit,
    onCompleteClick: () -> Unit,
    onEditClick: (Long, String, String) -> Unit,
    postId: Long,
    onErrorToast: (Throwable, Int) -> Unit,
    viewModel: MyProfileViewModel = hiltViewModel()
){
    val transactionCompleteUiState by viewModel.transactionCompleteUiState.collectAsStateWithLifecycle()
    val getMySpecificInformationUiState = viewModel.getMySpecificInformationUiState.collectAsStateWithLifecycle().value
    val deletePostUiState by viewModel.deletePostUiState.collectAsStateWithLifecycle()

    val (openDeleteBottomSheet, setOpenDeleteBottomSheet) = remember { mutableStateOf(false) }

    val context = LocalContext.current

    LaunchedEffect(postId) {
        viewModel.getMyPostDetail(postId = postId)
    }

    LaunchedEffect(deletePostUiState) {
        when (deletePostUiState) {
            is DeletePostUiState.Loading -> Unit
            is DeletePostUiState.Success -> {
                setOpenDeleteBottomSheet(false)
                makeToast(context, "게시글이 삭제되었습니다.")
                onCompleteClick()
            }
            is DeletePostUiState.Error -> {
                makeToast(context, "게시글 삭제 실패")
            }
        }
    }

    LaunchedEffect (transactionCompleteUiState){
        when(transactionCompleteUiState){
            is TransactionCompleteUiState.Loading -> Unit
            is TransactionCompleteUiState.Success ->{
                makeToast(context,"거래완료 성공")
            }
            is TransactionCompleteUiState.Error ->{
                makeToast(context,"거래완료 실패")
            }
        }
    }

    when (getMySpecificInformationUiState) {
        is GetMySpecificInformationUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("로딩 중...")
            }
        }

        is GetMySpecificInformationUiState.Error -> {
            onErrorToast(getMySpecificInformationUiState.exception, com.school_of_company.design_system.R.string.main_error)

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("게시글 정보를 불러오는 중 오류가 발생했습니다.")
            }
        }

        is GetMySpecificInformationUiState.Empty -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("게시글이 존재하지 않습니다.")
            }
        }

        is GetMySpecificInformationUiState.Success -> {
            val post = getMySpecificInformationUiState.data
                ReviewPostDetailScreen(
                    onBackClick = onBackClick,
                    data = getMySpecificInformationUiState.data,
                    onEditClick = {
                        onEditClick(post.id, post.type, post.mode)
                    },
                    onDeleteCompleteClick = onCompleteClick,
                    onTransactionCompleteCallBack = {
                        viewModel.transactionComplete(
                            body = TransactionCompleteRequestModel(
                                productId = postId,
                                otherMemberId = (getMySpecificInformationUiState).data.id
                            )
                        )
                    },
                    openDeleteBottomSheet = openDeleteBottomSheet,
                    setOpenDeleteBottomSheet = setOpenDeleteBottomSheet,
                    onDeleteCallBack = {
                        viewModel.deletePost(postId)
                    }
                )
        }
    }
}

@Composable
fun ReviewPostDetailScreen(
    modifier: Modifier = Modifier,
    data: Post,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteCompleteClick: () -> Unit,
    onDeleteCallBack: () -> Unit,
    onTransactionCompleteCallBack: () -> Unit,
    openDeleteBottomSheet: Boolean,
    setOpenDeleteBottomSheet: (Boolean) -> Unit
) {
    GwangSanTheme { colors, typography ->

        val pagerState = rememberPagerState(pageCount = { data.images.size })

        Box(
            modifier = modifier
                .fillMaxSize()
                .background(colors.white)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
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

                if (data.images.isEmpty()) {
                    Image(
                        painter = painterResource(id = com.school_of_company.design_system.R.drawable.gwangsan),
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
                        val image = data.images[page]
                        AsyncImage(
                            model = image.imageUrl,
                            contentDescription = "게시물 이미지 $page",
                            placeholder = painterResource(id = com.school_of_company.design_system.R.drawable.gwangsan),
                            error = painterResource(id = com.school_of_company.design_system.R.drawable.gwangsan),
                            fallback = painterResource(id = com.school_of_company.design_system.R.drawable.gwangsan),
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
                    repeat(data.images.size) { index ->
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

                com.school_of_company.design_system.componet.recycle.MyProfileUserLevel(
                    modifier = Modifier.padding(
                        horizontal = 24.dp,
                        vertical = 12.dp
                    ),
                    onClick = {},
                    data = data.member
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(color = colors.gray100)
                )

                Spacer(modifier = Modifier.height(24.dp))

                CleaningRequestCard(
                    title = data.title,
                    priceAndLocation = "${data.gwangsan} 광산",
                    description = data.content,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "이 게시글 삭제하기",
                    style = typography.label.copy(textDecoration = TextDecoration.Underline),
                    color = colors.error,
                    modifier = Modifier
                        .GwangSanClickable { setOpenDeleteBottomSheet(true) }
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
                        text = "수정하기",
                        backgroundColor = colors.white,
                        textColor = colors.main500,
                        onClick = { onEditClick() },
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
                        onClick = {
                            onTransactionCompleteCallBack()
                                  },
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

        if (openDeleteBottomSheet) {
            Dialog(onDismissRequest = { setOpenDeleteBottomSheet(false) }) {
                ProfileDialog(
                    onLogout = {
                        onDeleteCallBack()
                        setOpenDeleteBottomSheet(false)
                    },
                    onDismiss = {
                        setOpenDeleteBottomSheet(false)
                    },
                    titleText = "게시글 삭제",
                    contentText = "이 게시글을 삭제하시겠습니까?",
                    buttonText = "삭제하기"
                )
            }
        }
    }
}
