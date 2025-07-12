import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.school_of_company.design_system.componet.button.GwangSanEnableButton
import com.school_of_company.design_system.componet.button.GwangSanStateButton
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.CloseIcon
import com.school_of_company.design_system.componet.icons.DownArrowIcon
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.model.member.response.GetMemberResponseModel
import com.school_of_company.model.post.response.Post
import com.school_of_company.post.R
import com.school_of_company.profile.component.CleaningRequestCard
import com.school_of_company.profile.component.MyProfileUserLevel
import com.school_of_company.profile.viewmodel.MyProfileViewModel
import com.school_of_company.profile.viewmodel.uistate.GetMySpecificInformationUiState
import com.school_of_company.profile.viewmodel.uistate.MemberUiState
import com.school_of_company.ui.previews.GwangsanPreviews

@Composable
internal fun ReviewPostDetailRoute(
    onBackClick: () -> Unit,
    onCompleteClick: () -> Unit,
    postId: Long,
    onErrorToast: (Throwable, Int) -> Unit,
    viewModel: MyProfileViewModel = hiltViewModel()
){
    val getMyProfileUiState = viewModel.myProfileUiState.collectAsStateWithLifecycle().value
    val getMySpecificInformationUiState = viewModel.getMySpecificInformationUiState.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        viewModel.getMyPostDetail(postId = postId)
    }


    when (getMySpecificInformationUiState) {
        is GetMySpecificInformationUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("로딩 중...")
            }
        }

        is GetMySpecificInformationUiState.Error -> {
            onErrorToast(getMySpecificInformationUiState.exception, com.school_of_company.design_system.R.string.main_error)

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("게시글 정보를 불러오는 중 오류가 발생했습니다.")
            }
        }

        is GetMySpecificInformationUiState.Empty -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("게시글이 존재하지 않습니다.")
            }
        }

        is GetMySpecificInformationUiState.Success -> {
            if (getMyProfileUiState is MemberUiState.Success) {
                ReviewPostDetailScreen(
                    memBerData = getMyProfileUiState.data,
                    onBackClick = onBackClick,
                    data = getMySpecificInformationUiState.data,
                    onEditClick = { /* 수정 클릭 시 동작 */ },
                    onCompleteClick = onCompleteClick,
                )
            }
        }
    }
}
@Composable
fun ReviewPostDetailScreen(
    modifier: Modifier = Modifier,
    data: List<Post>,
    memBerData: GetMemberResponseModel,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onCompleteClick: () -> Unit
) {
    GwangSanTheme { colors, _ ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colors.white)
        ) {
            Column(
                modifier =Modifier
                    .weight(1f)
                    .padding(24.dp)
            ) {
                GwangSanSubTopBar(
                    startIcon = { DownArrowIcon(modifier = Modifier.GwangSanClickable { onBackClick() }) },
                    betweenText = "내 글"
                )

                Spacer(modifier = Modifier.height(24.dp))

                MyProfileUserLevel(
                    data = data.first(),
                    memberdata = memBerData
                )

                Spacer(modifier = Modifier.height(12.dp))

                CleaningRequestCard(
                    data = data.first()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
            ) {
                GwangSanEnableButton(
                    text = "수정",
                    backgroundColor = colors.white,
                    textColor = colors.gray500,
                    onClick = onEditClick,
                    modifier = Modifier
                        .weight(1f)
                        .border(
                            width = 1.dp,
                            color = colors.main500,
                            shape = RoundedCornerShape(8.dp)
                        )
                )

                GwangSanStateButton(
                    text = "거래완료",
                    onClick = onCompleteClick,
                    modifier = Modifier
                        .weight(1f)
                        .border(
                            width = 1.dp,
                            color = colors.main500,
                            shape = RoundedCornerShape(8.dp)
                        )
                )
            }
        }
    }
}
