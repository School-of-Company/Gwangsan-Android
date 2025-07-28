package com.school_of_company.main.view

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.school_of_company.design_system.R
import com.school_of_company.design_system.component.clickable.GwangSanClickable
import com.school_of_company.design_system.component.icons.BellIcon
import com.school_of_company.design_system.component.icons.MainTitle
import com.school_of_company.design_system.component.icons.ObjectIcon
import com.school_of_company.design_system.component.icons.ServiceIcon
import com.school_of_company.design_system.component.icons.UnReadBellIcon
import com.school_of_company.design_system.component.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.main.component.MainButton
import com.school_of_company.main.viewmodel.MainViewModel
import com.school_of_company.main.viewmodel.uistate.GetUnReadAlertUiState
import com.school_of_company.main.viewmodel.uistate.MemberUiState
import com.school_of_company.ui.previews.GwangsanPreviews
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
internal fun MainStartRoute(
    navigationToService: () -> Unit,
    navigationToObject: () -> Unit,
    navigationToNotice: () -> Unit,
    viewModel: MainViewModel = hiltViewModel()
) {

    val getMyProfileUiState by viewModel.myProfileUiState.collectAsStateWithLifecycle()
    val getUnReadAlertUiState by viewModel.getUnReadAlertUiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getMyProfile()
        viewModel.getUnReadAlert()
    }

    MainStartScreen(
        navigationToService = navigationToService,
        navigationToObject = navigationToObject,
        getMyProfileUiState = getMyProfileUiState,
        navigationToNotice = navigationToNotice,
        getUnReadAlertUiState = getUnReadAlertUiState
    )
}

@Composable
private fun MainStartScreen(
    modifier: Modifier = Modifier,
    navigationToService: () -> Unit,
    navigationToNotice: () -> Unit,
    navigationToObject: () -> Unit,
    getUnReadAlertUiState: GetUnReadAlertUiState,
    getMyProfileUiState: MemberUiState
) {
    GwangSanTheme { colors, typography ->

        val scrollState = rememberScrollState()

        val bannerImages = listOf(
            R.drawable.main2,
            R.drawable.main3,
            R.drawable.main4,
            R.drawable.main5,
            R.drawable.main6,
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
                .verticalScroll(scrollState)
        ) {
            GwangSanSubTopBar(
                startIcon = { MainTitle(modifier = Modifier.GwangSanClickable { }) },
                endIcon = {
                    when (getUnReadAlertUiState) {
                        is GetUnReadAlertUiState.Success -> {
                            if (getUnReadAlertUiState.data.unread) {
                                UnReadBellIcon(modifier = Modifier.GwangSanClickable { navigationToNotice() })
                            } else {
                                BellIcon(modifier = Modifier.GwangSanClickable { navigationToNotice() })
                            }
                        }
                        else -> BellIcon(modifier = Modifier.GwangSanClickable { navigationToNotice() })

                    }
                },
                modifier = Modifier.padding(
                    top = 56.dp,
                    start = 24.dp,
                    end = 24.dp
                )
            )

            Spacer(modifier = Modifier.padding(bottom = 20.dp))

            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 3.dp)
                    .height(280.dp),

            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Divider(
                        color = colors.gray400,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                    )

                    AutoSlideBanner(
                        imageIds = bannerImages,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                    )

                    Divider(
                        color = colors.gray400,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 3.dp)
                            .height(1.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column(modifier = Modifier.padding(horizontal = 24.dp)) {

                Text(
                    text = "광산구도시재생센터",
                    style = typography.titleMedium2,
                    color = colors.black,
                )

                Spacer(modifier = Modifier.height(8.dp))

                when (getMyProfileUiState) {
                    is MemberUiState.Loading -> {
                        Text(
                            text = "로딩중..",
                            style = typography.body4,
                            color = colors.gray500
                        )
                    }

                    is MemberUiState.Success -> {
                        Text(
                            text = getMyProfileUiState.data.placeName,
                            style = typography.body4,
                            color = colors.black
                        )
                    }

                    is MemberUiState.Error -> {
                        Text(
                            text = "요청 실패..",
                            style = typography.body4,
                            color = colors.gray500
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(bottom = 62.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    MainButton(
                        buttonText = "물건",
                        buttonIcon = { ServiceIcon() },
                        onClick = { navigationToObject() },
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(25.dp))

                    MainButton(
                        buttonText = "서비스",
                        buttonIcon = { ObjectIcon() },
                        onClick = { navigationToService() },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun AutoSlideBanner(
    modifier: Modifier = Modifier,
    imageIds: List<Int>,
    durationMillis: Long = 2650L
) {
    val pagerState = rememberPagerState(pageCount = { imageIds.size })
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        while (true) {
            delay(durationMillis)
            val nextPage = (pagerState.currentPage + 1) % imageIds.size
            coroutineScope.launch {
                pagerState.animateScrollToPage(
                    page = nextPage,
                    animationSpec = tween(durationMillis = 500)
                )
            }
        }
    }

    HorizontalPager(
        state = pagerState,
        modifier = modifier
    ) { page ->
        Image(
            painter = painterResource(id = imageIds[page]),
            contentDescription = "슬라이드 배너 이미지 $page",
            modifier = Modifier.fillMaxSize()
        )
    }
}

@GwangsanPreviews
@Composable
fun MainStartScreenPreview() {
    MainStartScreen(
        navigationToService = {},
        navigationToObject = {},
        getMyProfileUiState = MemberUiState.Loading,
        navigationToNotice = {},
        getUnReadAlertUiState = GetUnReadAlertUiState.Loading
    )
}

