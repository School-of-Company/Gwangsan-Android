package com.school_of_company.main.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.CloseIcon
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.main.component.Notice
import com.school_of_company.main.component.NoticeList

@Composable
fun NoticeScreen(
    modifier: Modifier = Modifier,
    items: List<Notice>,
    onBackClick: () -> Unit,
) {
    GwangSanTheme { colors, typography ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
            ) {
                Spacer(modifier = Modifier.height(52.dp))

                GwangSanSubTopBar(
                    startIcon = {
                        Box(
                            modifier = Modifier
                                .width(8.dp)
                                .height(14.dp)
                        )
                    },
                    betweenText = "알림",
                    endIcon = { CloseIcon(modifier = Modifier.GwangSanClickable { onBackClick() }) }
                )

                Spacer(modifier = Modifier.height(24.dp))

                NoticeList(
                    modifier = Modifier.fillMaxSize(),
                    items = items
                )

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NoticeScreenPreview() {
    val dummyNotices = listOf(
        Notice(id = 1, title = "📢 첫 번째 공지", content = "이것은 첫 번째 공지입니다.", image = null),
        Notice(id = 2, title = "📌 두 번째 공지", content = "다음은 중요한 사항입니다.", image = null),
        Notice(id = 3, title = "📎 세 번째 공지", content = "세 번째 공지의 내용입니다.", image = null)
    )

    NoticeScreen(
        items = dummyNotices,
        onBackClick = {},
    )
}
