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
internal  fun NoticeRoute(
    onBackClick: () -> Unit
){
    NoticeScreen(
        onBackClick = onBackClick,
        items = listOf(
            Notice(
                id = 1,
                title = "📢 서비스 점검 안내",
                content = "7월 20일 오전 2시부터 4시까지 서버 점검이 진행됩니다.",
                image = null
            ),
            Notice(
                id = 2,
                title = "🎉 신규 기능 출시",
                content = "채팅 기능과 알림 설정 기능이 새롭게 추가되었습니다!",
                image = null
            ),
            Notice(
                id = 3,
                title = "🚨 개인정보 처리방침 변경 안내",
                content = "개인정보 처리방침이 8월 1일부터 변경될 예정입니다. 꼭 확인해주세요.",
                image = null
            ),
            Notice(
                id = 4,
                title = "📌 자주 묻는 질문 정리",
                content = "많은 분들이 궁금해하시는 내용을 정리해두었습니다.",
                image = null
            ),
            Notice(
                id = 5,
                title = "📦 배송 지연 안내",
                content = "기상 악화로 인해 일부 지역의 배송이 지연되고 있습니다.",
                image = null
            )
        )
    )
}
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
