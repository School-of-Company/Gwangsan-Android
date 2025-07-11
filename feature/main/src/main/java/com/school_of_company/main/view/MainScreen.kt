package com.school_of_company.main.view

import android.util.Log
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.school_of_company.design_system.R
import com.school_of_company.design_system.componet.button.gwangsanfloatingbutton.GwangSanFloatingButton
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.dropdown.GwangSanSwitchButton
import com.school_of_company.design_system.componet.dropdown.state.GwangSanSwitchState
import com.school_of_company.design_system.componet.icons.DownArrowIcon
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.main.component.MainList
import com.school_of_company.main.viewmodel.MainViewModel
import com.school_of_company.main.viewmodel.uistate.GetMainListUiState
import com.school_of_company.model.enum.Mode
import com.school_of_company.model.enum.Type
import com.school_of_company.ui.previews.GwangsanPreviews

@Composable
internal fun MainRoute(
    navigationToPost: (Mode) -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    moDeselectedType: Type,
    viewModel: MainViewModel = hiltViewModel()
) {
    val getMainListUiState by viewModel.getMainListUiState.collectAsStateWithLifecycle()
    val swipeRefreshLoading by viewModel.swipeRefreshLoading.collectAsStateWithLifecycle(initialValue = false)
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
        mainCallBack = {
            viewModel.getMainList(
                type = moDeselectedType,
                mode = selectedMode
            )
        },
        onErrorToast = onErrorToast,
        getMainListUiState = getMainListUiState,
        switchState = switchState,
        onSwitchStateChange = { switchState = it },
        swipeRefreshState = swipeRefreshState,
        betweenText = betweenText
    )
}

@Composable
private fun MainScreen(
    modifier: Modifier = Modifier,
    navigationToPostService: () -> Unit,
    mainCallBack: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    getMainListUiState: GetMainListUiState,
    switchState: GwangSanSwitchState,
    swipeRefreshState: SwipeRefreshState,
    onSwitchStateChange: (GwangSanSwitchState) -> Unit,
    betweenText: String
) {
    GwangSanTheme { colors, _ ->
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
                        top = 24.dp
                    )
            ) {
                GwangSanSubTopBar(
                    startIcon = { DownArrowIcon(modifier = Modifier.GwangSanClickable { }) },
                    betweenText = betweenText
                )

                Spacer(modifier = Modifier.height(38.dp))

                GwangSanSwitchButton(
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
                                items = getMainListUiState.getMainListResponse,
                            )
                        }

                        is GetMainListUiState.Empty -> {
                            MainList(items = emptyList())
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
                            onErrorToast(getMainListUiState.exception, R.string.main_error)
                        }
                    }
                }
            }

            GwangSanFloatingButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp)
            ) {
                navigationToPostService()
            }
        }
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    val dummyState = rememberSwipeRefreshState(isRefreshing = false)

    MainScreen(
        navigationToPostService = {},
        mainCallBack = {},
        onErrorToast = { _, _ -> },
        getMainListUiState = GetMainListUiState.Loading,
        switchState = GwangSanSwitchState.NEED,
        onSwitchStateChange = {},
        swipeRefreshState = dummyState,
        betweenText = "물건"
    )
}