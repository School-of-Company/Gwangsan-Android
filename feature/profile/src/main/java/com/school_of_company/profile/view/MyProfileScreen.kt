package com.school_of_company.profile.view

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.school_of_company.design_system.componet.toast.makeToast
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.model.member.response.GetMemberResponseModel
import com.school_of_company.profile.component.BrightnessProgressBar
import com.school_of_company.profile.component.GwangSanMoney
import com.school_of_company.profile.component.MyInformation
import com.school_of_company.profile.component.MyProfileExerciseButton
import com.school_of_company.profile.component.MyReviewListItem
import com.school_of_company.profile.component.MySpecialListScreen
import com.school_of_company.profile.component.ProfileDialog
import com.school_of_company.profile.viewmodel.MyProfileViewModel
import com.school_of_company.profile.viewmodel.uistate.GetMyPostUiState
import com.school_of_company.profile.viewmodel.uistate.LogoutUiState
import com.school_of_company.profile.viewmodel.uistate.MemberUiState

@Composable
internal fun MyProfileRoute(
    onMyWritingClick: () -> Unit,
    onMyReviewClick: () -> Unit,
    onMyInformationEditClick: () -> Unit,
    onMyWritingDetailClick: (Long) -> Unit,
    onLogoutClick: () -> Unit,
    onErrorToast: (Throwable, Int) -> Unit,
    viewModel: MyProfileViewModel = hiltViewModel()
) {
    val memberUiState = viewModel.myProfileUiState.collectAsStateWithLifecycle().value
    val getMyPostUiState = viewModel.getMyPostUiState.collectAsStateWithLifecycle().value
    val logoutUiState by viewModel.logoutUiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getMyProfile()
        viewModel.getMyPost()
    }

    when (logoutUiState) {
        is LogoutUiState.Loading -> Unit
        is LogoutUiState.Error -> {
            makeToast(context, "로그아웃 실패")
        }
        is LogoutUiState.Success -> {
            onLogoutClick()
            makeToast(context, "로그아웃 성공")
        }
    }

    when (memberUiState) {
        is MemberUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "로딩 중...",
                )
            }
        }

        is MemberUiState.Error -> {
            onErrorToast(memberUiState.exception, com.school_of_company.design_system.R.string.main_error)
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "회원 정보를 불러오는 중 오류가 발생했습니다.",
                )
            }
        }

        is MemberUiState.Empty -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "회원 정보가 없습니다.",
                )
            }
        }

        is MemberUiState.Success -> {
            MyProfileScreen(
                data = memberUiState.data,
                onMyWritingClick = onMyWritingClick,
                onMyReviewClick = onMyReviewClick,
                getMyPostUiState = getMyPostUiState,
                onMyWritingDetailClick = onMyWritingDetailClick,
                onMyInformationEditClick = onMyInformationEditClick,
                onLogoutCallBack = { viewModel.logout() }
            )
        }
    }
}

@Composable
private fun MyProfileScreen(
    modifier: Modifier = Modifier,
    onMyWritingDetailClick: (Long) -> Unit,
    data: GetMemberResponseModel,
    onLogoutCallBack: () -> Unit,
    onMyInformationEditClick: () -> Unit,
    onMyWritingClick: () -> Unit,
    getMyPostUiState: GetMyPostUiState,
    onMyReviewClick: () -> Unit,
) {
    val (openLogoutDialog, setOpenLogoutDialog) = rememberSaveable { mutableStateOf(false) }

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
                    startIcon = { Box(modifier = Modifier.size(24.dp)) },
                    betweenText = "프로필",
                    modifier = Modifier.padding(horizontal = 24.dp),
                )
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item {
                MyInformation(
                    onModifyClick = { onMyInformationEditClick()  },
                    data = data,
                    onLogoutClick = { setOpenLogoutDialog(true) }
                )
            }

            item {
                HorizontalDivider(
                    thickness = 12.dp,
                    color = colors.gray200
                )
            }

            item {
                MySpecialListScreen(
                    data = data,
                    modifier = Modifier.padding(24.dp),
                )
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item {
                BrightnessProgressBar(
                    brightnessLevel = data.light,
                    maxLevel = 10,
                    modifier = Modifier.padding(24.dp),
                )
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item {
                GwangSanMoney(
                    miningAmount = data.gwangsan,
                    modifier = Modifier.padding(24.dp),
                )
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item {
                HorizontalDivider(
                    thickness = 12.dp,
                    color = colors.gray200
                )
            }

            item {
                Text(
                    text = "내 활동",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                )
            }

            item {
                Row(modifier = Modifier.padding(horizontal = 24.dp)) {
                    MyProfileExerciseButton(
                        modifier = Modifier.weight(1f),
                        onClick = { onMyReviewClick() },
                        buttonText = "내가 받은 후기"
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    MyProfileExerciseButton(
                        onClick = { onMyWritingClick() },
                        buttonText = "내가 작성한 후기",
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 52.dp)
                    )
                }
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
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                )
            }

            when (getMyPostUiState) {
                is GetMyPostUiState.Loading -> {
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

                is GetMyPostUiState.Error -> {
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

                is GetMyPostUiState.Empty -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "게시물이 없습니다.",
                                style = typography.body2,
                                color = colors.gray600,
                            )
                        }
                    }
                }

                is GetMyPostUiState.Success -> {
                        items(getMyPostUiState.data) { item ->
                            MyReviewListItem(
                                onClick = { onMyWritingDetailClick(item.id) },
                                data = item
                            )
                    }
                }
            }
        }

        if (openLogoutDialog) {
            Dialog(onDismissRequest = { setOpenLogoutDialog(false) }) {
                ProfileDialog(
                    onLogout = {
                        onLogoutCallBack()
                        setOpenLogoutDialog(false)
                    },
                    onDismiss = { setOpenLogoutDialog(false) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MyProfileScreenPreview() {
    val mockData = GetMemberResponseModel(
        memberId = 1L,
        nickname = "홍길동",
        light = 5,
        gwangsan = 300,
        specialties = listOf("Android", "Kotlin", "Jetpack Compose"),
        placeName = "광산",
        description = "안녕하세요, 홍길동입니다."
    )

    MyProfileScreen(
        data = mockData,
        onMyInformationEditClick = {},
        onMyWritingClick = {},
        onMyReviewClick = {},
        onMyWritingDetailClick = {},
        getMyPostUiState = GetMyPostUiState.Empty,
        onLogoutCallBack = {}
    )
}
