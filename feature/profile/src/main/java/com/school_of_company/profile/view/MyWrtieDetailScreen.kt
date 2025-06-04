import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.componet.button.GwangSanEnableButton
import com.school_of_company.design_system.componet.button.GwangSanStateButton
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.CloseIcon
import com.school_of_company.design_system.componet.icons.DownArrowIcon
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.profile.component.CleaningRequestCard
import com.school_of_company.profile.component.MyProfileUserLevel

@Composable
fun ReviewPostDetailScreen(
    modifier: Modifier = Modifier,
    coverImage: String?,
    name: String,
    description: String,
    level: Int,
    postTitle: String,
    postLocationAndPrice: String,
    postDescription: String,
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
                    startIcon = { DownArrowIcon(modifier = Modifier.GwangSanClickable { /* 뒤로 가기 */ }) },
                    betweenText = "내 글",
                    endIcon = { CloseIcon(modifier = Modifier.GwangSanClickable { /* 닫기 */ }) }
                )

                Spacer(modifier = Modifier.height(24.dp))

                MyProfileUserLevel(
                    name = name,
                    coverImage = coverImage,
                    description = description,
                    level = level
                )

                Spacer(modifier = Modifier.height(12.dp))

                CleaningRequestCard(
                    title = postTitle,
                    priceAndLocation = postLocationAndPrice,
                    description = postDescription
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

@Preview
@Composable
fun PreViewReviewPostDetailScreen(

){
    ReviewPostDetailScreen(
        coverImage = "https://example.com/cover_image.jpg",
        name = "사용자 이름",
        description = "사용자 설명",
        level = 3,
        postTitle = "게시글 제목",
        postLocationAndPrice = "위치 및 가격",
        postDescription = "게시글 설명",
        onEditClick = { /* 수정 클릭 시 동작 */ },
        onCompleteClick = { /* 거래완료 클릭 시 동작 */ }
    )
}