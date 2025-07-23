package com.school_of_company.chat.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.school_of_company.chat.component.ChatListItem
import com.school_of_company.chat.viewmodel.ChatViewModel
import com.school_of_company.chat.viewmodel.uistate.GetChatRoomUiState
import com.school_of_company.design_system.R
import com.school_of_company.design_system.component.clickable.GwangSanClickable
import com.school_of_company.design_system.component.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme

@Composable
internal fun ChatRoute(
    onCloseClick: () -> Unit,
    onChatClick: (Long) -> Unit,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val swipeRefreshLoading by viewModel.swipeRefreshLoading.collectAsStateWithLifecycle()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = swipeRefreshLoading)

    val getChatRoomUiState by viewModel.getChatRoomUiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getChatRoom()
    }

    ChatScreen(
        swipeRefreshState = swipeRefreshState,
        getChatRoom = viewModel::getChatRoom,
        getChatRoomUiState = getChatRoomUiState,
        onCloseClick = onCloseClick,
        onChatClick = onChatClick
    )
}

@Composable
private fun ChatScreen(
    modifier: Modifier = Modifier,
    swipeRefreshState: SwipeRefreshState,
    getChatRoom: () -> Unit,
    getChatRoomUiState: GetChatRoomUiState,
    onCloseClick: () -> Unit,
    onChatClick: (Long) -> Unit,
) {
    GwangSanTheme { colors, typography ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
                .padding(bottom = 56.dp)
        ) {

            Spacer(modifier = Modifier.height(43.dp))

            GwangSanSubTopBar(
                startIcon = { Spacer(modifier = Modifier.size(24.dp)) },
                betweenText = "채팅",
                endIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.close),
                        contentDescription = "닫기",
                        modifier = Modifier
                            .size(24.dp)
                            .GwangSanClickable { onCloseClick() }
                    )
                },
                modifier = Modifier.padding(all = 24.dp),
            )

            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { getChatRoom() },
                indicator = { state, refreshTrigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = refreshTrigger,
                        contentColor = colors.main500
                    )
                }
            ) {
                when (getChatRoomUiState) {
                    is GetChatRoomUiState.Loading -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .background(color = colors.white)
                        ) {
                            Text(
                                text = "채팅방 가져오는중..",
                                style = typography.titleMedium2,
                                color = colors.gray500
                            )
                        }
                    }

                    is GetChatRoomUiState.Success -> {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(
                                items = getChatRoomUiState.data,
                                key = { it.roomId }
                            ) { item ->
                                ChatListItem(
                                    item = item,
                                    onClick = { onChatClick(item.product.productId) }
                                )
                            }
                        }
                    }

                    is GetChatRoomUiState.Empty -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .background(color = colors.white)
                        ) {
                            Text(
                                text = "채팅방이 없어요..",
                                style = typography.titleMedium2,
                                color = colors.gray500
                            )
                        }
                    }

                    is GetChatRoomUiState.Error -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .background(color = colors.white)
                        ) {
                            Text(
                                text = "채팅방를 불러오는데 실패했어요..",
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