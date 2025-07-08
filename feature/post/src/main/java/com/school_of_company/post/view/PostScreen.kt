package com.school_of_company.post.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.school_of_company.design_system.component.progress.GwangSanTopBarProgress
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.DownArrowIcon
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.model.enum.Mode
import com.school_of_company.model.enum.Type
import com.school_of_company.post.viewmodel.PostViewModel
import com.school_of_company.ui.previews.GwangsanPreviews
import kotlinx.coroutines.launch

@Composable
internal fun PostRoute(
    type: Type,
    mode: Mode,
    onBackClick: () -> Unit,
    onComplete: () -> Unit
) {
    PostScreen(
        type = type,
        mode = mode,
        onBackClick = onBackClick,
        onComplete = onComplete
    )
}

@Composable
private fun PostScreen(
    type: Type,
    mode: Mode,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onComplete: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()

    val viewModel: PostViewModel = hiltViewModel()

    LaunchedEffect(type, mode) {
        viewModel.onTypeChange(type)
        viewModel.onModeChange(mode)
    }

    val progressRatios = listOf(0.3f, 0.6f, 1.0f)
    val currentProgress = progressRatios.getOrElse(pagerState.currentPage) { 0.3f }

    GwangSanTheme { _, _ ->
        Column(modifier = modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(52.dp))

            GwangSanSubTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                startIcon = {
                    DownArrowIcon(
                        modifier = Modifier
                            .width(8.dp)
                            .height(14.dp)
                            .GwangSanClickable(onClick = {
                                coroutineScope.launch {
                                    if (pagerState.currentPage > 0) {
                                        pagerState.animateScrollToPage(page = pagerState.currentPage - 1)
                                    } else {
                                        onBackClick()
                                    }
                                }
                            })
                    )
                },
                betweenText = "해주세요",
            )

            Spacer(modifier = Modifier.height(8.dp))

            GwangSanTopBarProgress(
                progressRatio = currentProgress,
                modifier = Modifier.fillMaxWidth()
            )

            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when(page) {
                    0 -> PostInputRoute(
                        type = type,
                        mode = mode,
                        onBackClick = {
                            coroutineScope.launch {
                                if (pagerState.currentPage > 0) {
                                    pagerState.animateScrollToPage(page = pagerState.currentPage - 1)
                                } else {
                                    onBackClick()
                                }
                            }
                        },
                        onNextClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(page = 1)
                            }
                        },
                        viewModel = viewModel
                    )

                    1 -> PostWriteRoute(
                        type = type,
                        mode = mode,
                        onBackClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(page = 0)
                            }
                        },
                        onNextClick = { _, _ ->
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(page = 2)
                            }
                        },
                        viewModel = viewModel
                    )

                    2 -> PostFinalRoute(
                        type = type,
                        mode = mode,
                        onSubmitClick = onComplete,
                        onEditClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(page = 1)
                            }
                        },
                        onBackClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(page = 1)
                            }
                        },
                        onErrorToast = { throwable, messageRes ->
                        },
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@GwangsanPreviews
@Composable
private fun PostScreenPreview() {
    PostRoute(
        type = Type.OBJECT,
        mode = Mode.GIVER,
        onComplete = {},
        onBackClick = {},
    )
}