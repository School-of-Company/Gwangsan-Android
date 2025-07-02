package com.school_of_company.content.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import com.school_of_company.content.component.ReportBottomSheet
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
internal fun ReadMoreRoute(
    onBackClick: () -> Unit,
    onMyProfileClick: () -> Unit,
    onChatClick: () -> Unit,
    onReviewClick: (Int, String) -> Unit,
    onReportClick: (String, String) -> Unit
) {

    ReadMoreScreen(
        onBackClick = onBackClick,
        onMyProfileClick = onMyProfileClick,
        onChatClick = onChatClick,
        coverImage = "",
        name = "",
        postTitle = "",
        description = "",
        level = 0,
        postLocationAndPrice = "",
        postDescription = "",
        onReviewClick = onReviewClick,
        onReportClick = onReportClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReadMoreScreen(
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
    onReviewClick: (Int, String) -> Unit,
    onReportClick: (String, String) -> Unit
) {

    val (openReportBottomSheet, isOpenReportBottomSheet) = rememberSaveable { mutableStateOf(false) }
    val (openReviewBottomSheet, isOpenReviewBottomSheet) = rememberSaveable { mutableStateOf(false) }

    GwangSanTheme { colors, typography ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colors.white)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                GwangSanSubTopBar(
                    startIcon = { DownArrowIcon(modifier = Modifier.GwangSanClickable { onBackClick() }) },
                    betweenText = "해주세요",
                    endIcon = { CloseIcon(modifier = Modifier.GwangSanClickable { onMyProfileClick() }) },
                    modifier = Modifier.padding(
                        top = 24.dp,
                        start = 24.dp,
                        end = 24.dp
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                Image(
                    painter = rememberAsyncImagePainter(model = coverImage),
                    contentDescription = "Content ReadMoreScreen Cover Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                )

                MyProfileUserLevel(
                    name = name,
                    description = description,
                    level = level,
                    modifier = Modifier.padding(
                        horizontal = 24.dp,
                        vertical = 12.dp
                    )
                )

                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = colors.gray100)
                )

                Spacer(modifier = Modifier.height(24.dp))

                CleaningRequestCard(
                    title = postTitle,
                    priceAndLocation = postLocationAndPrice,
                    description = postDescription,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "이 게시글 신고하기",
                    style = typography.label,
                    color = colors.error,
                    modifier = Modifier
                        .GwangSanClickable { isOpenReportBottomSheet(true) }
                        .padding(horizontal = 24.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 16.dp,
                        horizontal = 24.dp
                    )
            ) {
                GwangSanEnableButton(
                    text = "채팅하기",
                    backgroundColor = colors.white,
                    textColor = colors.main500,
                    onClick = onChatClick,
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
                    onClick = { isOpenReviewBottomSheet(true) },
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

    if (openReportBottomSheet) {
        Dialog(onDismissRequest = { isOpenReportBottomSheet(false) }) {
            ReportBottomSheet(
                onDismiss = { isOpenReportBottomSheet(false) },
                onSubmit = onReportClick,
            )
        }
    }

    if (openReviewBottomSheet) {
        Dialog(onDismissRequest = { isOpenReviewBottomSheet(false) }) {
            ReviewBottomSheet(
                onDismiss = { isOpenReviewBottomSheet(false) },
                onSubmit = onReviewClick,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewReadMoreScreen() {
    ReadMoreScreen(
        coverImage = "https://ibb.co/qqMZkSg",
        name = "모태한",
        description = "첨단 1동",
        level = 8,
        postTitle = "집 청소좀 해주세요",
        postLocationAndPrice = "5000 광산",
        postDescription = "(지역명) 집 청소 도와주실 분 찾습니다.\n바닥, 화장실 위주로 부탁드리며, 청소 도구는 준비되어 있습니다.\n(희망 날짜)에 가능하신 분 연락 주세요!\n급여는 (금액)입니다.",
        onChatClick = {},
        onBackClick = {},
        onMyProfileClick = {},
        onReportClick = {_, _ ->},
        onReviewClick = {_, _ ->}
    )
}