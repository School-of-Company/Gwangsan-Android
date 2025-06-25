package com.school_of_company.content.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.school_of_company.design_system.theme.color.GwangSanColor

@Composable
private fun ReportScreenContent(
    onDismiss: () -> Unit = {},
    onSubmit: (String, String) -> Unit = { _, _ -> },
    initialType: String = "",
    initialReason: String = ""
) {
    GwangSanTheme { colors, typography ->
        Box(modifier = Modifier.fillMaxSize()) {
            ReadMoreScreen(
                coverImage = null,
                name = "모태한",
                description = "첨단 1동",
                level = 8,
                postTitle = "집 청소좀 해주세요",
                postLocationAndPrice = "5000 광산",
                postDescription = "(지역명) 집 청소 도와주실 분 찾습니다.\n바닥, 화장실 위주로 부탁드리며, 청소 도구는 준비되어 있습니다.\n(희망 날짜)에 가능하신 분 연락 주세요!\n급여는 (금액)입니다.",
                onBackClick = {},
                onMyProfileClick = {},
                onChatClick = {},
                onCompleteClick = {},
                onReportClick = {}
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.BottomCenter
            ) {
                Surface(
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 24.dp)
                    ) {
                        Spacer(modifier = Modifier.height(24.dp))

                        ReportBottomSheet(
                            modifier = Modifier.fillMaxWidth(),
                            onDismiss = onDismiss,
                            onSubmit = onSubmit,
                            initialSelectedOption = initialType,
                            initialReportContent = initialReason
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ReviewScreenContent(
    onDismiss: () -> Unit = {},
    onSubmit: (Float, String) -> Unit = { _, _ -> }
) {
    GwangSanTheme { colors, typography ->
        Box(modifier = Modifier.fillMaxSize()) {
            ReadMoreScreen(
                coverImage = null,
                name = "모태한",
                description = "첨단 1동",
                level = 8,
                postTitle = "집 청소좀 해주세요",
                postLocationAndPrice = "5000 광산",
                postDescription = "(지역명) 집 청소 도와주실 분 찾습니다.\n바닥, 화장실 위주로 부탁드리며, 청소 도구는 준비되어 있습니다.\n(희망 날짜)에 가능하신 분 연락 주세요!\n급여는 (금액)입니다.",
                onBackClick = {},
                onMyProfileClick = {},
                onChatClick = {},
                onCompleteClick = {},
                onReportClick = {}
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.BottomCenter
            ) {
                Surface(
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 24.dp)
                    ) {
                        Spacer(modifier = Modifier.height(24.dp))

                        ReviewBottomSheet(
                            modifier = Modifier.fillMaxWidth(),
                            onDismiss = onDismiss,
                            onSubmit = onSubmit
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewReportScreen() {
    ReportScreenContent()
}

@Preview(showBackground = true)
@Composable
fun PreviewReviewScreen() {
    ReviewScreenContent()
}

@Preview(showBackground = true, name = "신고 활성화 상태")
@Composable
fun PreviewReportScreenEnabled() {
    ReportScreenContent(
        initialType = "사기",
        initialReason = "거짓 정보를 올렸어요",
        onDismiss = {},
        onSubmit = { type, reason ->
            println("신고 유형: $type / 신고 사유: $reason")
        }
    )
}
