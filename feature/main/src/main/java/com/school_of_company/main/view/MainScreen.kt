package com.school_of_company.main.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.school_of_company.design_system.component.button.gwangsanfloatingbutton.GwangSanFloatingButton
import com.school_of_company.design_system.component.clickable.GwangSanClickable
import com.school_of_company.design_system.component.dropdown.GwangSanSwitchButton
import com.school_of_company.design_system.component.dropdown.state.GwangSanSwitchState
import com.school_of_company.design_system.component.icons.DownArrowIcon
import com.school_of_company.design_system.component.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.main.component.MainList
import com.school_of_company.main.viewmodel.MainViewModel
import com.school_of_company.main.viewmodel.uistate.GetMainListUiState
import com.school_of_company.model.enum.Mode
import com.school_of_company.model.enum.Type
import kotlinx.collections.immutable.toPersistentList

@Composable
internal fun MainRoute(
    navigationToPost: (Mode) -> Unit,
    navigationToDetail: (Long) -> Unit,
    onBackClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    moDeselectedType: Type,
    viewModel: MainViewModel = hiltViewModel()
) {
    val getMainListUiState by viewModel.getMainListUiState.collectAsStateWithLifecycle()
    val swipeRefreshLoading by viewModel.swipeRefreshLoading.collectAsStateWithLifecycle(
        initialValue = false
    )
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = swipeRefreshLoading)

    var switchState by remember { mutableStateOf(GwangSanSwitchState.NEED) }

    val selectedMode = when (switchState) {
        GwangSanSwitchState.NEED -> Mode.RECEIVER
        GwangSanSwitchState.REQUEST -> Mode.GIVER
    }

    val betweenText = when (moDeselectedType) {
        Type.OBJECT -> "물건"
        Type.SERVICE -> "서비스"
    }

    LaunchedEffect(selectedMode, moDeselectedType) {
        viewModel.getMainList(
            mode = selectedMode,
            type = moDeselectedType
        )
    }

    MainScreen(
        navigationToPostService = { navigationToPost(selectedMode) },
        onBackClick = onBackClick,
        mainCallBack = {
            viewModel.getMainList(
                type = moDeselectedType,
                mode = selectedMode
            )
        },
        getMainListUiState = getMainListUiState,
        switchState = switchState,
        onSwitchStateChange = { switchState = it },
        swipeRefreshState = swipeRefreshState,
        betweenText = betweenText,
        navigationToDetail = navigationToDetail,
        moDeselectedType = moDeselectedType
    )
}

@Composable
private fun MainScreen(
    modifier: Modifier = Modifier,
    navigationToPostService: () -> Unit,
    navigationToDetail: (Long) -> Unit,
    mainCallBack: () -> Unit,
    onBackClick: () -> Unit,
    getMainListUiState: GetMainListUiState,
    moDeselectedType: Type,
    switchState: GwangSanSwitchState,
    swipeRefreshState: SwipeRefreshState,
    onSwitchStateChange: (GwangSanSwitchState) -> Unit,
    betweenText: String
) {
    GwangSanTheme { colors, typography ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 24.dp,
                        end = 24.dp,
                        top = 52.dp
                    )
            ) {
                GwangSanSubTopBar(
                    startIcon = {
                        DownArrowIcon(
                            modifier = Modifier
                                .width(8.dp)
                                .height(14.dp)
                                .GwangSanClickable { onBackClick() }
                        )
                    },
                    betweenText = betweenText
                )

                Spacer(modifier = Modifier.height(38.dp))

                GwangSanSwitchButton(
                    type = moDeselectedType,
                    stateOn = GwangSanSwitchState.NEED,
                    stateOff = GwangSanSwitchState.REQUEST,
                    initialValue = switchState,
                    onCheckedChanged = {
                        onSwitchStateChange(it)
                        mainCallBack()
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                SwipeRefresh(
                    state = swipeRefreshState,
                    onRefresh = { mainCallBack() },
                    indicator = { state, refreshTrigger ->
                        SwipeRefreshIndicator(
                            state = state,
                            refreshTriggerDistance = refreshTrigger,
                            contentColor = colors.main500
                        )
                    }
                ) {
                    when (getMainListUiState) {
                        is GetMainListUiState.Success -> {
                            MainList(
                                items = getMainListUiState.getMainListResponse.toPersistentList(),
                                onClick = navigationToDetail
                            )
                        }

                        is GetMainListUiState.Empty -> {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState())
                                    .background(color = colors.white)
                            ) {
                                Text(
                                    text = "정보가 없습니다..",
                                    style = typography.titleMedium2,
                                    color = colors.gray500
                                )
                            }
                        }

                        is GetMainListUiState.Loading -> {
                            Column(
                                modifier = Modifier
                                    .background(
                                        color = colors.white,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .fillMaxSize()
                            ) {
                                repeat(10) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(135.dp)
                                            .padding(vertical = 10.dp)
                                            .background(
                                                color = colors.white,
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                    )
                                }
                            }
                        }

                        is GetMainListUiState.Error -> {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState())
                                    .background(color = colors.white)
                            ) {
                                Text(
                                    text = "정보를 불러오는데 실패했어요..",
                                    style = typography.titleMedium2,
                                    color = colors.gray500
                                )
                            }
                        }
                    }
                }
            }

            GwangSanFloatingButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        end = 24.dp,
                        bottom = 60.dp
                    )
            ) {
                navigationToPostService()
            }
        }
    }
}