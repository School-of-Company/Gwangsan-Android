package com.school_of_company.profile.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.model.member.response.GetAllMemberResponseModel
import com.school_of_company.model.post.response.Post
import com.school_of_company.profile.component.BrightnessProgressBar
import com.school_of_company.profile.component.MyReviewList
import com.school_of_company.profile.component.OtherInformation
import com.school_of_company.profile.component.OtherPersonIntroduce
import com.school_of_company.profile.viewmodel.MyProfileViewModel
import com.school_of_company.profile.viewmodel.uistate.OtherPersonGetUistate

@Composable
internal fun OtherPersonProfileRoute(
    onErrorToast: (Throwable, Int) -> Unit,
    memberId: Long,
    viewModel: MyProfileViewModel = hiltViewModel(),
) {
    val otherPersonUiState = viewModel.otherPersonUiState.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        viewModel.otherPersonGetMyProfile(memberId = memberId)
    }

    when (otherPersonUiState) {
        is OtherPersonGetUistate.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "로딩 중입니다...")
            }
        }

        is OtherPersonGetUistate.Error -> {
            onErrorToast(
                otherPersonUiState.exception,
                com.school_of_company.design_system.R.string.main_error
            )

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "프로필 정보를 불러오는 데 실패했습니다.")
            }
        }

        is OtherPersonGetUistate.Empty -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "해당 유저의 프로필이 존재하지 않습니다.")
            }
        }

        is OtherPersonGetUistate.Success -> {
            OtherPersonProfileScreen(
                data = otherPersonUiState.data,
                item = listOf()
            )
        }
    }
}

@Composable
private fun OtherPersonProfileScreen(
    modifier: Modifier = Modifier,
    data: GetAllMemberResponseModel,
    item: List<Post>
) {

    GwangSanTheme { colors, typography ->

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp)
                .background(color = colors.white)
        ) {
            item {
                GwangSanSubTopBar(
                    startIcon = { Box(modifier = Modifier.size(24.dp)) },
                    betweenText = "프로필",
                    modifier = Modifier.padding(24.dp),
                )
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item {
                OtherInformation(
                    data = data,
                )
            }

            item {
                HorizontalDivider(thickness = 12.dp, color = colors.gray200)
            }

            item {
                OtherPersonIntroduce(data = data)
            }

            item {
                BrightnessProgressBar(
                    brightnessLevel = data.light,
                    maxLevel = 10,
                    modifier = Modifier.padding(24.dp),
                )
            }

            item {
                HorizontalDivider(
                    thickness = 12.dp,
                    color = colors.gray200
                )
            }

            item {
                Text(
                    text = "게시글",
                    style = typography.body1,
                    color = colors.black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                )
            }

            item {
                MyReviewList(
                    onClick = {},
                    items = item
                )
            }
        }
    }
}

@Preview
@Composable
private fun OtherPersonProfileScreenPreview() {
    val fakeData = GetAllMemberResponseModel(
        memberId = 999L,
        nickname = "테스트 유저",
        placeName = "서울 강남구",
        light = 6,
        description = "테스트 유저입니다.",
        specialties = listOf("특기1", "특기2")
    )

    OtherPersonProfileScreen(
        data = fakeData,
        item = listOf(

        )
    )
}

