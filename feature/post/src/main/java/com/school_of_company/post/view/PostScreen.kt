package com.school_of_company.post.view

import androidx.compose.foundation.background
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
    editPostId: Long? = null,
    onBackClick: () -> Unit,
    onCreateComplete: () -> Unit,
    onEditComplete: () -> Unit
) {
    PostScreen(
        type = type,
        mode = mode,
        onBackClick = onBackClick,
        editPostId = editPostId,
        onCreateComplete = onCreateComplete,
        onEditComplete = onEditComplete
    )
}

@Composable
private fun PostScreen(
    modifier: Modifier = Modifier,
    type: Type,
    mode: Mode,
    editPostId: Long? = null,
    onBackClick: () -> Unit,
    onCreateComplete: () -> Unit,
    onEditComplete: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()

    val viewModel: PostViewModel = hiltViewModel()

    val isEditMode = editPostId != null

    LaunchedEffect(type, mode) {
        viewModel.onTypeChange(type)
        viewModel.onModeChange(mode)

        if (isEditMode && editPostId != null) {
            viewModel.loadPostForEdit(editPostId)
        }
    }

    val progressRatios = listOf(0.3f, 0.6f, 1.0f)
    val currentProgress = progressRatios.getOrElse(pagerState.currentPage) { 0.3f }

    GwangSanTheme { colors, _ ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
        ) {
            Spacer(modifier = Modifier.padding(top = 52.dp))

            GwangSanSubTopBar(
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
            )

            Spacer(modifier = Modifier.height(8.dp))

            GwangSanTopBarProgress(
                progressRatio = currentProgress,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(bottom = 48.dp))

            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when(page) {

                    0 -> PostWriteRoute(
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
                        onNextClick = { _, _ ->
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(page = 1)
                            }
                        },
                        viewModel = viewModel
                    )

                    1 -> PostInputRoute(
                        type = type,
                        mode = mode,
                        onBackClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(page = 0)
                            }
                        },
                        onNextClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(page = 2)
                            }
                        },
                        viewModel = viewModel
                    )

                    2 -> PostFinalRoute(
                        type = type,
                        mode = mode,
                        onSubmitClick = {
                            if (isEditMode) {
                                onEditComplete()
                            } else {
                                onCreateComplete()
                            }
                        },
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
        onBackClick = {},
        onCreateComplete = {},
        onEditComplete = {}
    )
}