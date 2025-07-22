package com.school_of_company.main.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.school_of_company.design_system.component.icons.CloseIcon
import com.school_of_company.design_system.component.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.main.component.NoticeList
import com.school_of_company.main.viewmodel.MainViewModel
import com.school_of_company.main.viewmodel.uistate.GetAlertUiState

@Composable
internal  fun NoticeRoute(
    onBackClick: () -> Unit,
    viewModel: MainViewModel = hiltViewModel()
){
    val swipeRefreshLoading by viewModel.swipeRefreshLoading.collectAsStateWithLifecycle(
        initialValue = false
    )

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = swipeRefreshLoading)

    val getAlertUiState by viewModel.getAlertUiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getAlert()
    }

    NoticeScreen(
        onBackClick = onBackClick,
        swipeRefreshState = swipeRefreshState,
        getAlertCallBack = {viewModel.getAlert()},
        getAlertUiState = getAlertUiState
    )
}

@Composable
private fun NoticeScreen(
    modifier: Modifier = Modifier,
    getAlertUiState: GetAlertUiState,
    swipeRefreshState: SwipeRefreshState,
    getAlertCallBack: () -> Unit,
    onBackClick: () -> Unit,
) {
    GwangSanTheme { colors, typography ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
            ) {
                Spacer(modifier = Modifier.height(52.dp))

                GwangSanSubTopBar(
                    startIcon = {
                        Box(
                            modifier = Modifier
                                .width(8.dp)
                                .height(14.dp)
                        )
                    },
                    betweenText = "알림",
                    endIcon = { CloseIcon(modifier = Modifier.GwangSanClickable { onBackClick() }) }
                )

                Spacer(modifier = Modifier.height(24.dp))

                SwipeRefresh(
                    state = swipeRefreshState,
                    onRefresh = { getAlertCallBack() },
                    indicator = { state, refreshTrigger ->
                        SwipeRefreshIndicator(
                            state = state,
                            refreshTriggerDistance = refreshTrigger,
                            contentColor = colors.main500
                        )
                    }
                ) {
                    when (getAlertUiState) {
                        is GetAlertUiState.Success -> {
                            NoticeList(
                                items = getAlertUiState.data,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 16.dp)
                            )
                        }

                        is GetAlertUiState.Error -> {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState())
                                    .background(color = colors.white)
                            ) {
                                Text(
                                    text = "알림을 조회하는데 실패했습니다..",
                                    style = typography.titleMedium2,
                                    color = colors.gray500
                                )
                            }
                        }

                        is GetAlertUiState.Empty -> {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState())
                                    .background(color = colors.white)
                            ) {
                                Text(
                                    text = "알림 없습니다..",
                                    style = typography.titleMedium2,
                                    color = colors.gray500
                                )
                            }
                        }

                        is GetAlertUiState.Loading -> {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState())
                                    .background(color = colors.white)
                            ) {
                                Text(
                                    text = "알림 로딩중..",
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
}