package com.school_of_company.profile.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.school_of_company.model.review.response.ReviewResponseModel
import com.school_of_company.profile.component.MyProfileReviewListItem
import com.school_of_company.profile.ui.model.ReviewResponseUi
import com.school_of_company.profile.viewmodel.MyProfileViewModel
import com.school_of_company.profile.viewmodel.uistate.GetMyReviewUiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentSet

@Composable
internal fun MyReceiveReviewRoute(
    onBackClick: () -> Unit,
    onPostClick: (Long) -> Unit,
    viewModel: MyProfileViewModel = hiltViewModel(),
) {
    val swipeRefreshLoading by viewModel.swipeRefreshLoading.collectAsStateWithLifecycle(
        initialValue = false
    )
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = swipeRefreshLoading)

    val getMyReviewUiState by viewModel.getMyReviewUiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getMyReview()
    }

    MyReceiveReviewScreen(
        onBackClick = onBackClick,
        getMyReviewUiState = getMyReviewUiState,
        getMyReceiveCallBack = { viewModel.getMyReview() },
        swipeRefreshState = swipeRefreshState,
        onPostClick = onPostClick
    )
}

@Composable
private fun MyReceiveReviewScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    getMyReviewUiState: GetMyReviewUiState,
    swipeRefreshState: SwipeRefreshState,
    getMyReceiveCallBack: () -> Unit,
    onPostClick: (Long) -> Unit

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
                betweenText = "내가 받은 후기",
            )

            Spacer(modifier = Modifier.padding(bottom = 16.dp))

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
                when (getMyReviewUiState) {
                    is GetMyReviewUiState.Success -> {
                        MyReceiveReviewProfileList(
                            onPostClick = onPostClick,
                            items = getMyReviewUiState.review.toPersistentList(),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp)
                        )
                    }

                    is GetMyReviewUiState.Error -> {
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

                    is GetMyReviewUiState.Empty -> {
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

                    is GetMyReviewUiState.Loading -> {
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
fun MyReceiveReviewProfileList(
    items: PersistentList<ReviewResponseUi>,
    onPostClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items) { review ->
            MyProfileReviewListItem(
                onClick = onPostClick,
                data = review
            )
        }
    }
}
