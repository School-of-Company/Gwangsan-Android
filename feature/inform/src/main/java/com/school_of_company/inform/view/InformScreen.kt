package com.school_of_company.inform.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.school_of_company.design_system.component.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.inform.component.InformList
import com.school_of_company.inform.viewmodel.NoticeViewModel
import com.school_of_company.inform.viewmodel.uistate.GetAllNoticeUiState
import com.school_of_company.inform.viewmodel.uistate.MemberUiState
import com.school_of_company.model.notice.response.GetAllNoticeResponseModel
import com.school_of_company.ui.previews.GwangsanPreviews

@Composable
internal fun InformRoute(
    onNextClick: (Long) -> Unit,
    viewModel: NoticeViewModel = hiltViewModel()
) {
    val swipeRefreshLoading by viewModel.swipeRefreshLoading.collectAsStateWithLifecycle()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = swipeRefreshLoading)

    val getAllNoticeUiState by viewModel.getAllNoticeUiState.collectAsStateWithLifecycle()
    val getMyProfileUiState by viewModel.getMyProfileUiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getAllNotice()
        viewModel.getMyProfile()
    }

    InformScreen(
        swipeRefreshState = swipeRefreshState,
        getAllNotice = { viewModel.getAllNotice() },
        informList = getAllNoticeUiState,
        onNextClick = onNextClick,
        getMyProfileUiState = getMyProfileUiState
    )
}

@Composable
private fun InformScreen(
    modifier: Modifier = Modifier,
    swipeRefreshState: SwipeRefreshState,
    getAllNotice: () -> Unit,
    informList: GetAllNoticeUiState,
    getMyProfileUiState: MemberUiState,
    onNextClick: (Long) -> Unit
) {
    GwangSanTheme { colors, typography ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
                .padding(
                    top = 56.dp,
                    bottom = 20.dp,
                    start = 24.dp,
                    end = 24.dp
                )
        ) {
            GwangSanSubTopBar(
                startIcon = { Spacer(modifier = Modifier.size(24.dp)) },
                betweenText = "공지",
                endIcon = { Spacer(modifier = Modifier.size(24.dp)) },
                modifier = Modifier.padding(bottom = 16.dp)
            )

            when (getMyProfileUiState) {
                is MemberUiState.Loading -> {
                    Text(
                        text = "지점명 가져오는 중..",
                        style = typography.titleMedium2,
                        color = colors.black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "지점명 가져오는 중..",
                        style = typography.body5,
                        color = colors.gray400,
                    )
                }

                is MemberUiState.Success -> {
                    Text(
                        text = "${getMyProfileUiState.data.placeName} 공지입니다",
                        style = typography.titleMedium2,
                        color = colors.black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = getMyProfileUiState.data.placeName,
                        style = typography.body5,
                        color = colors.gray400,
                    )
                }

                is MemberUiState.Error -> {
                    Text(
                        text = "지점을 찾을 수 없습니다..",
                        style = typography.titleMedium2,
                        color = colors.black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "지점을 찾을 수 없습니다..",
                        style = typography.body5,
                        color = colors.gray400,
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { getAllNotice() },
                indicator = { state, refreshTrigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = refreshTrigger,
                        contentColor = colors.main500
                    )
                }
            ) {
                when (informList) {
                    is GetAllNoticeUiState.Loading -> Unit
                    is GetAllNoticeUiState.Success -> {
                        InformList(
                            items = informList.data,
                            onClick = onNextClick
                        )
                    }

                    is GetAllNoticeUiState.Error -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .background(color = colors.white)
                        ) {
                            Text(
                                text = "공지를 불러오는데 실패했어요..",
                                style = typography.titleMedium2,
                                color = colors.gray500
                            )
                        }
                    }

                    is GetAllNoticeUiState.Empty -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .background(color = colors.white)
                        ) {
                            Text(
                                text = "공지가 없어요..",
                                style = typography.titleMedium2,
                                color = colors.gray500
                            )
                        }
                    }
                }
            }
        }
    }
}

@GwangsanPreviews
@Composable
private fun InformScreenPreview() {
    val dummyList = listOf(
        GetAllNoticeResponseModel(
            title = "당분간 거래중지입니다",
            content = "어떤 이의 거래 중지 의사로 인해 요청사항...",
            images = emptyList(),
            id = 0
        )
    )

    InformScreen(
        informList = GetAllNoticeUiState.Empty,
        onNextClick = {},
        swipeRefreshState = SwipeRefreshState(isRefreshing = false),
        getAllNotice = {},
        modifier = Modifier,
        getMyProfileUiState = MemberUiState.Loading
    )
}
