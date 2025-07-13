package com.school_of_company.profile.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.CloseIcon
import com.school_of_company.design_system.componet.icons.DownArrowIcon
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.model.post.response.Post
import com.school_of_company.model.review.response.ReviewResponseModel
import com.school_of_company.profile.component.MyProfileReviewListItem
import com.school_of_company.profile.viewmodel.MyProfileViewModel
import com.school_of_company.profile.viewmodel.uistate.GetMyReviewUiState
import com.school_of_company.profile.viewmodel.uistate.GetMySpecificInformationUiState
import com.school_of_company.profile.viewmodel.uistate.OtherReviewUIState

@Composable
internal fun OtherReviewRoute(
    onBackClick: () -> Unit,
    memberId: Long,
    viewModel: MyProfileViewModel = hiltViewModel()
) {
    val getOtherReviewUiState by viewModel.otherReviewUIState.collectAsStateWithLifecycle()
    val getSpecificPostUiState by viewModel.getMySpecificInformationUiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getOtherReview(
            memberId = memberId
        )
    }

    LaunchedEffect(getOtherReviewUiState) {
        if (getOtherReviewUiState is OtherReviewUIState.Success) {
            val firstProductId = (getOtherReviewUiState as OtherReviewUIState.Success).data.productId
            if (firstProductId != null) {
                viewModel.getMyPostDetail(firstProductId)
            }
        }
    }

    if (getOtherReviewUiState is OtherReviewUIState.Success &&
        getSpecificPostUiState is GetMySpecificInformationUiState.Success
    ) {
        val reviewList = (getOtherReviewUiState as GetMyReviewUiState.Success).review
        val image  = (getSpecificPostUiState as GetMySpecificInformationUiState.Success).data

        OtherReviewScreen(
            onBackClick = onBackClick,
            image = image,
            item = reviewList
        )
    }
}

@Composable
private fun OtherReviewScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    image: Post,
    item: List<ReviewResponseModel>
) {
    GwangSanTheme { colors, _ ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
                .padding(24.dp)
        ) {
            GwangSanSubTopBar(
                startIcon = { DownArrowIcon(modifier = Modifier.GwangSanClickable { onBackClick() }) },
                betweenText = "내가 받은 후기",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OtherReviewScreenList(
                items = item,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun OtherReviewScreenList(
    items: List<ReviewResponseModel>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items) { review ->
            MyProfileReviewListItem(
                data = review,
            )
        }
    }
}
