package com.school_of_company.content.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.componet.button.GwangSanEnableButton
import com.school_of_company.design_system.componet.button.GwangSanStateButton
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.CloseIcon
import com.school_of_company.design_system.componet.icons.DownArrowIcon
import com.school_of_company.design_system.componet.recycle.CleaningRequestCard
import com.school_of_company.design_system.componet.recycle.MyProfileUserLevel
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme

@Composable
fun ReadMoreScreen(
    modifier: Modifier = Modifier,
    coverImage: String?,
    name: String,
    description: String,
    level: Int,
    postTitle: String,
    postLocationAndPrice: String,
    postDescription: String,
    onBackClick: () -> Unit,
    onMyProfileClick: () -> Unit,
    onChatClick: () -> Unit,
    onCompleteClick: () -> Unit,
    onReportClick: () -> Unit
) {
    GwangSanTheme { colors, typography ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colors.white)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(24.dp)
            ) {
                GwangSanSubTopBar(
                    startIcon = { DownArrowIcon(modifier = Modifier.GwangSanClickable { onBackClick() }) },
                    betweenText = "해주세요",
                    endIcon = { CloseIcon(modifier = Modifier.GwangSanClickable { onMyProfileClick() }) }
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

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "이 게시글 신고하기",
                    style = typography.label,
                    color = colors.error,
                    modifier = Modifier.GwangSanClickable { onReportClick() }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                GwangSanEnableButton(
                    text = "채팅하기",
                    backgroundColor = colors.white,
                    textColor = colors.main500,
                    onClick = onChatClick,
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, colors.main500, RoundedCornerShape(8.dp))
                )

                GwangSanStateButton(
                    text = "거래완료",
                    onClick = onCompleteClick,
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, colors.main500, RoundedCornerShape(8.dp))
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewReadMoreScreen() {
    ReadMoreScreen(
        coverImage = "https://cdn.pixabay.com/photo/2023/01/23/12/55/cleaning-7738035_1280.jpg",
        name = "모태한",
        description = "첨단 1동",
        level = 8,
        postTitle = "집 청소좀 해주세요",
        postLocationAndPrice = "5000 광산",
        postDescription = "(지역명) 집 청소 도와주실 분 찾습니다.\n바닥, 화장실 위주로 부탁드리며, 청소 도구는 준비되어 있습니다.\n(희망 날짜)에 가능하신 분 연락 주세요!\n급여는 (금액)입니다.",
        onChatClick = {},
        onCompleteClick = {},
        onBackClick = {},
        onMyProfileClick = {},
        onReportClick = {}
    )
}