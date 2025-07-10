package com.school_of_company.inform.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.school_of_company.design_system.R
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.DownArrowIcon
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.inform.viewmodel.NoticeViewModel
import com.school_of_company.inform.viewmodel.uistate.GetSpecificNoticeUiState
import com.school_of_company.model.notice.response.SpecificNoticeImageModel

@Composable
internal fun InformDetailRoute(
    noticeId: Long,
    onBackClick: () -> Unit,
    viewModel: NoticeViewModel = hiltViewModel()
) {
    val getSpecificNoticeUiState by viewModel.getSpecificNoticeUiState.collectAsStateWithLifecycle()

    LaunchedEffect(noticeId) {
        viewModel.getSpecificNotice(noticeId = noticeId)
    }

    InformDetailScreen(
        getSpecificNoticeUiState = getSpecificNoticeUiState,
        onBackClick = onBackClick
    )
}

@Composable
private fun InformDetailScreen(
    getSpecificNoticeUiState: GetSpecificNoticeUiState,
    onBackClick: () -> Unit = {},
) {

    GwangSanTheme { colors, typography ->

        when (getSpecificNoticeUiState) {
            is GetSpecificNoticeUiState.Success -> {

                val pagerState = rememberPagerState(pageCount = { getSpecificNoticeUiState.data.images.size })

                Scaffold(
                    containerColor = colors.white,
                    topBar = {
                        GwangSanSubTopBar(
                            startIcon = {
                                DownArrowIcon(
                                    modifier = Modifier
                                        .padding(start = 16.dp)
                                        .GwangSanClickable { onBackClick() }
                                        .size(
                                            width = 8.dp,
                                            height = 14.dp
                                        )
                                )
                            },
                            betweenText = "공지",
                            endIcon = { Spacer(modifier = Modifier.size(24.dp)) },
                            modifier = Modifier.padding(top = 56.dp),
                        )
                    }
                ) { padding ->
                    Column(
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(280.dp)
                        ) { page ->
                            val image = getSpecificNoticeUiState.data.images[page]
                            AsyncImage(
                                model = image.imageUrl,
                                contentDescription = "공지 이미지 $page",
                                placeholder = painterResource(id = R.drawable.gwangsan),
                                error = painterResource(id = R.drawable.gwangsan),
                                fallback = painterResource(id = R.drawable.gwangsan),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                        ) {
                            repeat(getSpecificNoticeUiState.data.images.size) { index ->
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

                        Column(
                            modifier = Modifier.padding(
                                horizontal = 20.dp,
                                vertical = 24.dp
                            )
                        ) {
                            Text(
                                text = getSpecificNoticeUiState.data.title,
                                style = typography.titleMedium2,
                                color = colors.black
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = getSpecificNoticeUiState.data.place,
                                style = typography.body3,
                                color = colors.black
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = getSpecificNoticeUiState.data.content,
                                style = typography.body4,
                                color = colors.black
                            )
                        }
                    }
                }
            }

            is GetSpecificNoticeUiState.Loading -> Unit
            is GetSpecificNoticeUiState.Error -> {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = colors.white)
                ) {
                    Text(
                        text = "공지 정보를 불러오는데 실패했습니다..",
                        style = typography.titleMedium2,
                        color = colors.gray500
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun InformDetailScreenPreview() {
    val dummyNotice = GetSpecificNoticeUiState.Success(
        data = com.school_of_company.model.notice.response.GetSpecificNoticeResponseModel(
            id = 1L,
            title = "공지사항 제목입니다",
            content = "이것은 공지사항 내용입니다.\n여러 줄로 구성되어 있으며, 실제로는 서버에서 내려온 공지 내용을 보여줍니다.",
            place = "본점",
            images = listOf(
                SpecificNoticeImageModel(
                    imageId = 1L,
                    imageUrl = "https://via.placeholder.com/600x400"
                ),
                SpecificNoticeImageModel(
                    imageId = 1L,
                    imageUrl = "https://via.placeholder.com/600x400"
                ),
            ),
            createdAt = "2023-10-01",
            role = "ADMIN"
        )
    )

    InformDetailScreen(
        getSpecificNoticeUiState = dummyNotice,
        onBackClick = {}
    )
}
