package com.school_of_company.profile.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.DownArrowIcon
import com.school_of_company.design_system.componet.toast.makeToast
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.model.member.response.GetAllMemberResponseModel
import com.school_of_company.model.post.response.Image
import com.school_of_company.model.post.response.Post
import com.school_of_company.profile.component.BrightnessProgressBar
import com.school_of_company.profile.component.MyProfileExerciseButton
import com.school_of_company.profile.component.MyReviewListItem
import com.school_of_company.profile.component.OtherInformation
import com.school_of_company.profile.component.OtherPersonIntroduce
import com.school_of_company.profile.viewmodel.MyProfileViewModel
import com.school_of_company.profile.viewmodel.uistate.GetMyPostUiState
import com.school_of_company.profile.viewmodel.uistate.OtherGetPostUiState
import com.school_of_company.profile.viewmodel.uistate.OtherPersonGetUistate

@Composable
internal fun OtherPersonProfileRoute(
    onErrorToast: (Throwable, Int) -> Unit,
    onBackClick: () -> Unit,
    memberId: Long,
    onOtherReviewClick: (Long) -> Unit,
    onOtherWritingDetailClick: (Long) -> Unit,
    viewModel: MyProfileViewModel = hiltViewModel(),
) {
    val otherPersonUiState = viewModel.otherPersonUiState.collectAsStateWithLifecycle().value
    val otherGetPostUiState by viewModel.otherGetPostUiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.otherPersonGetMyProfile(memberId = memberId)
        viewModel.otherGetPost(memberId = memberId)
    }

    when (otherPersonUiState) {
        is OtherPersonGetUistate.Loading -> Unit

        is OtherPersonGetUistate.Error -> {
            makeToast(context = context, "정보를 볼러오는데 실패했습니다.")
        }

        is OtherPersonGetUistate.Success -> {
            OtherPersonProfileScreen(
                data = otherPersonUiState.data,
                onBackClick = onBackClick,
                onOtherReviewClick = onOtherReviewClick,
                otherGetPostUiState = otherGetPostUiState,
                onOtherWritingDetailClick = onOtherWritingDetailClick
            )
        }

        is OtherPersonGetUistate.Empty -> {
            makeToast(context = context, "정보가 없습니다.")
        }
    }

}

@Composable
private fun OtherPersonProfileScreen(
    modifier: Modifier = Modifier,
    data: GetAllMemberResponseModel,
    onBackClick: () -> Unit,
    onOtherReviewClick: (Long) -> Unit,
    onOtherWritingDetailClick: (Long) -> Unit,
    otherGetPostUiState: OtherGetPostUiState,
) {

    GwangSanTheme { colors, typography ->

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
                .padding(top = 80.dp)
        ) {
            item {
                GwangSanSubTopBar(
                    startIcon = {
                        DownArrowIcon(modifier = Modifier.GwangSanClickable { onBackClick() })
                    },
                    betweenText = "프로필",
                    modifier = Modifier.padding(horizontal = 24.dp),
                )
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item {
                OtherInformation(
                    data = data,
                    modifier = Modifier.padding(start = 24.dp)
                )
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item {
                HorizontalDivider(
                thickness = 12.dp,
                color = colors.gray200)
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                OtherPersonIntroduce(
                    data = data,
                    modifier = Modifier.padding(24.dp)
                )
            }

            item {
                HorizontalDivider(
                    thickness = 12.dp,
                    color = colors.gray200,
                )
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item {
                BrightnessProgressBar(
                    brightnessLevel = data.light,
                    maxLevel = 10,
                    modifier = Modifier.padding(24.dp)
                )
            }

            item {
                HorizontalDivider(
                    thickness = 12.dp,
                    color = colors.gray200
                )
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {

                    Text(
                        text = "후기",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colors.black,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    MyProfileExerciseButton(
                        onClick = { onOtherReviewClick(data.memberId) },
                        buttonText = "${data.nickname}의 후기",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 40.dp)
                    )
                }
            }

            item {
                HorizontalDivider(
                    thickness = 12.dp,
                    color = colors.gray200
                )
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item {
                Text(
                    text = "게시글",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                )
            }
            when (otherGetPostUiState) {
                is OtherGetPostUiState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "로딩 중...",
                                style = typography.body2,
                                color = colors.gray600
                            )
                        }
                    }
                }

                is OtherGetPostUiState.Error -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "게시글을 불러오지 못했습니다.",
                                style = typography.body2,
                                color = colors.gray600
                            )
                        }
                    }
                }

                is OtherGetPostUiState.Empty -> {
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
                        ) {
                            Text(
                                text = "게시물이 없습니다.",
                                style = typography.body2,
                                color = colors.gray600,
                            )
                        }
                    }
                }

                is OtherGetPostUiState.Success -> {
                    items(otherGetPostUiState.data) { item ->
                        MyReviewListItem(
                            onClick = { onOtherWritingDetailClick(item.id) },
                            data = item
                        )
                    }
                }
            }
        }
    }
}


