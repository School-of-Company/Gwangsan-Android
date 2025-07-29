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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.school_of_company.design_system.component.clickable.GwangSanClickable
import com.school_of_company.design_system.component.dialog.GwangsanDialog
import com.school_of_company.design_system.component.icons.CloseIcon
import com.school_of_company.design_system.component.toast.makeToast
import com.school_of_company.design_system.component.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.main.component.NoticeList
import com.school_of_company.main.viewmodel.MainViewModel
import com.school_of_company.main.viewmodel.uistate.GetAlertUiState
import com.school_of_company.main.viewmodel.uistate.TransactionCompleteUiState
import com.school_of_company.model.alert.response.GetAlertResponseModel
import com.school_of_company.model.enum.AlertType
import com.school_of_company.model.post.request.TransactionCompleteRequestModel
import kotlinx.collections.immutable.toPersistentList

@Composable
internal  fun NoticeRoute(
    onBackClick: () -> Unit,
    navigationToDetail: (Long) -> Unit,
    viewModel: MainViewModel = hiltViewModel()
){

    val swipeRefreshLoading by viewModel.swipeRefreshLoading.collectAsStateWithLifecycle(
        initialValue = false
    )

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = swipeRefreshLoading)

    val getAlertUiState by viewModel.getAlertUiState.collectAsStateWithLifecycle()
    val transactionCompleteUiState by viewModel.transactionCompleteUiState.collectAsStateWithLifecycle()

    val (openBottomSheet, setOpenBottomSheet) = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getAlert()
    }

    NoticeScreen(
        onBackClick = onBackClick,
        swipeRefreshState = swipeRefreshState,
        getAlertCallBack = {viewModel.getAlert()},
        getAlertUiState = getAlertUiState,
        transactionCompletedCallBack = { sourceId, sendMemberId: Long ->
            viewModel.transactionComplete(
                body = TransactionCompleteRequestModel(
                    productId = sourceId,
                    otherMemberId =  sendMemberId
                )
            )
        },
        transactionCompleteUiState = transactionCompleteUiState,
        openBottomSheet = openBottomSheet,
        setOpenBottomSheet = setOpenBottomSheet,
        navigationToDetail = navigationToDetail
    )
}

@Composable
private fun NoticeScreen(
    modifier: Modifier = Modifier,
    transactionCompletedCallBack: (Long, Long) -> Unit,
    getAlertUiState: GetAlertUiState,
    swipeRefreshState: SwipeRefreshState,
    openBottomSheet: Boolean,
    setOpenBottomSheet: (Boolean) -> Unit,
    navigationToDetail: (Long) -> Unit,
    transactionCompleteUiState: TransactionCompleteUiState,
    getAlertCallBack: () -> Unit,
    onBackClick: () -> Unit,
) {
    val selectedSourceId = remember { mutableStateOf<Long?>(null) }
    val selectedMemberId = remember { mutableStateOf<Long?>(null) }

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
                                items = getAlertUiState.data.toPersistentList(),
                                onClick = { sourceId, sendMemberId, alertType ->
                                    if (alertType == AlertType.TRADE_COMPLETE) {
                                        navigationToDetail(sourceId)
                                    }else if(alertType == AlertType.OTHER_MEMBER_TRADE_COMPLETE) {
                                        selectedSourceId.value = sourceId
                                        selectedMemberId.value = sendMemberId
                                        setOpenBottomSheet(true)
                                    }else {
                                        Unit
                                    }
                                },
                                modifier = Modifier.fillMaxSize()
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

                    when (transactionCompleteUiState) {
                        is TransactionCompleteUiState.Loading -> Unit

                        is TransactionCompleteUiState.Success -> {
                            makeToast(context = LocalContext.current, "거래 완료")
                        }

                        is TransactionCompleteUiState.Error -> {
                            makeToast(context = LocalContext.current, "거래 실패")
                        }

                        else -> Unit
                    }

                }


            }
            if (openBottomSheet) {
                Dialog(onDismissRequest = { setOpenBottomSheet(false) }) {
                    GwangsanDialog(
                        onLogout = {
                            transactionCompletedCallBack(
                                selectedSourceId.value ?: return@GwangsanDialog,
                                selectedMemberId.value ?: 0L
                            )
                            setOpenBottomSheet(false)
                        },
                        onDismiss = {
                            setOpenBottomSheet(false)
                        },
                        titleText = "거래 완료",
                        contentText = "정말 거래 완료를 하시겠습니까?",
                        buttonText = "거래 완료"
                    )
                }
            }
        }
    }
}