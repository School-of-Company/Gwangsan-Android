package com.school_of_company.profile.view

import MyReviewProfileList
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.DownArrowIcon
import com.school_of_company.design_system.componet.toast.makeToast
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.model.post.response.Post
import com.school_of_company.model.review.response.ReviewResponseModel
import com.school_of_company.profile.component.MyProfileReviewListItem
import com.school_of_company.profile.viewmodel.MyProfileViewModel
import com.school_of_company.profile.viewmodel.uistate.GetMyReviewUiState
import com.school_of_company.profile.viewmodel.uistate.GetMySpecificInformationUiState
import javax.annotation.meta.When

@Composable
internal fun MyReceiveReviewRoute(
    onBackClick: () -> Unit,
    viewModel: MyProfileViewModel = hiltViewModel(),
) {
    val getMyReviewUiState by viewModel.getMyReviewUiState.collectAsStateWithLifecycle()
    val getSpecificPostUiState by viewModel.getMySpecificInformationUiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getMyReview()
    }


    LaunchedEffect(getMyReviewUiState) {
        if (getMyReviewUiState is GetMyReviewUiState.Success) {
            val firstProductId = (getMyReviewUiState as GetMyReviewUiState.Success).review.firstOrNull()?.productId
            if (firstProductId != null) {
                viewModel.getMyPostDetail(firstProductId)
            }
        }
    }

    when (getMyReviewUiState) {
        is GetMyReviewUiState.Loading -> {
            makeToast(context, "로딩중")
        }

        is GetMyReviewUiState.Empty -> {
            makeToast(context, "데이터가 비어있습니다.")
        }

        is GetMyReviewUiState.Error -> {
            makeToast(context,"오류")
        }

        is GetMyReviewUiState.Success -> {
            val reviewList = (getMyReviewUiState as GetMyReviewUiState.Success).review

            when (getSpecificPostUiState) {
                is GetMySpecificInformationUiState.Success -> {
                    val image = (getSpecificPostUiState as GetMySpecificInformationUiState.Success).data
                    MyReceiveReviewScreen(
                        onBackClick = onBackClick,
                        image = image,
                        item = reviewList,
                    )
                }

                is GetMySpecificInformationUiState.Loading -> {
                    // 게시글 로딩 중이면 여기에 처리
                }

                is GetMySpecificInformationUiState.Empty -> {
                    // 게시글 데이터가 비어있을 때
                }

                is GetMySpecificInformationUiState.Error -> {
                    makeToast(context,"게시글 오류옴")
                }
            }

        }
    }
}
@Composable
private fun MyReceiveReviewScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    image: Post,
    item: List<ReviewResponseModel>
) {
    GwangSanTheme { colors, _ ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp)
                .background(color = colors.white)
        ) {
            GwangSanSubTopBar(
                startIcon = { DownArrowIcon(modifier = Modifier.GwangSanClickable { onBackClick() }) },
                betweenText = "내가 받은 후기",
                modifier = Modifier.padding(24.dp),
            )

            Spacer(modifier = Modifier.height(12.dp))

            MyReceiveReviewProfileList(
                items = item,
                image = image,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun MyReceiveReviewProfileList(
    items: List<ReviewResponseModel>,
    image: Post,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items) { review ->
            MyProfileReviewListItem(
                data = review,
                image = image
            )
        }
    }
}
