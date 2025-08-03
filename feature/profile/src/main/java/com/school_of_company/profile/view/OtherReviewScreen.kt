package com.school_of_company.profile.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.school_of_company.design_system.component.clickable.GwangSanClickable
import com.school_of_company.design_system.component.icons.DownArrowIcon
import com.school_of_company.design_system.component.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.model.review.response.ImagesList
import com.school_of_company.model.review.response.ReviewResponseModel
import com.school_of_company.profile.component.MyProfileReviewListItem
import com.school_of_company.profile.ui.model.ReviewResponseUi
import com.school_of_company.profile.viewmodel.MyProfileViewModel
import com.school_of_company.profile.viewmodel.uistate.OtherReviewUIState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Composable
internal fun OtherReviewRoute(
    onBackClick: () -> Unit,
    memberId: Long,
    viewModel: MyProfileViewModel = hiltViewModel()
) {

    val swipeRefreshLoading by viewModel.swipeRefreshLoading.collectAsStateWithLifecycle(
        initialValue = false
    )
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = swipeRefreshLoading)

    val getOtherReviewUiState by viewModel.otherReviewUIState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getOtherReview(memberId = memberId)
    }

    OtherReviewScreen(
        onBackClick = onBackClick,
        otherReviewUIState = getOtherReviewUiState,
        getMyReceiveCallBack = {viewModel.getOtherReview(memberId = memberId )},
        swipeRefreshState = swipeRefreshState,
    )
}

@Composable
private fun OtherReviewScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    otherReviewUIState: OtherReviewUIState,
    getMyReceiveCallBack: () -> Unit,
    swipeRefreshState: SwipeRefreshState,
) {
    GwangSanTheme { colors, typography ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
                .padding(24.dp)
        ) {
            Spacer(modifier = Modifier.padding(top = 56.dp))

            GwangSanSubTopBar(
                startIcon = { DownArrowIcon(modifier = Modifier.GwangSanClickable { onBackClick() }) },
                betweenText = if (otherReviewUIState is OtherReviewUIState.Success) {
                    val data = otherReviewUIState as OtherReviewUIState.Success
                    if (data.data.isNotEmpty()) {
                        "${data.data.first().reviewerName}님의 후기"
                    } else {
                        "받은 후기"
                    }
                } else {
                    "받은 후기"
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { getMyReceiveCallBack() },
                indicator = { state, refreshTrigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = refreshTrigger,
                        contentColor = colors.main500
                    )
                }
            ) {
                when (otherReviewUIState) {
                    is OtherReviewUIState.Success -> {
                        OtherReviewScreenList(
                            items = otherReviewUIState.data.toPersistentList(),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp)
                        )
                    }

                    is OtherReviewUIState.Error -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .background(color = colors.white)
                        ) {
                            Text(
                                text = "리뷰를 불러오는데 실패했습니다..",
                                style = typography.titleMedium2,
                                color = colors.gray500
                            )
                        }
                    }

                    is OtherReviewUIState.Empty -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .background(color = colors.white)
                        ) {
                            Text(
                                text = "리뷰가 없습니다..",
                                style = typography.titleMedium2,
                                color = colors.gray500
                            )
                        }
                    }

                    is OtherReviewUIState.Loading -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .background(color = colors.white)
                        ) {
                            Text(
                                text = "리뷰 로딩중..",
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

@Composable
fun OtherReviewScreenList(
    items: PersistentList<ReviewResponseUi>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items) { review ->
            MyProfileReviewListItem(
                data = review,
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
private fun OtherReviewScreenPreview() {

    OtherReviewScreen(
        otherReviewUIState = OtherReviewUIState.Loading,
        onBackClick = {},
        getMyReceiveCallBack = {},
        swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)
    )
}
