package com.school_of_company.inform.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.CloseIcon
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.inform.component.InformItem

// 임시 모델 (API 나오면 model 패키지로 이동)
private data class Inform(
    val title: String,
    val description: String,
    val imageUrl: String? = null
)

@Composable
internal fun InformRoute(
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
) {

    InformScreen(
        informList = listOf(
            Inform(
                title = "당분간 거래중지입니다",
                description = "어떤 이의 거래 중지 의사로 인해 요청사항...",
                imageUrl = null
            )
        ),
        onBackClick = onBackClick,
        onNextClick = onNextClick
    )
}

@Composable
private fun InformScreen(
    modifier: Modifier = Modifier,
    informList: List<Inform>,
    onBackClick: () -> Unit = {},
    onNextClick: () -> Unit
) {
    GwangSanTheme { colors, typography ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
                .verticalScroll(rememberScrollState())
                .padding(
                    top = 24.dp,
                    bottom = 20.dp,
                    start = 24.dp,
                    end = 24.dp
                )
        ) {
            GwangSanSubTopBar(
                startIcon = { Spacer(modifier = Modifier.size(24.dp)) },
                betweenText = "공지",
                endIcon = {
                    Box(modifier = Modifier.padding(end = 24.dp)) {
                        CloseIcon(
                            modifier = Modifier
                                .size(24.dp)
                                .GwangSanClickable { onBackClick() }
                        )
                    }
                },
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "지점명 공지입니다",
                style = typography.titleMedium2,
                color = colors.black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "본점",
                style = typography.body5,
                color = colors.gray400,
            )

            Spacer(modifier = Modifier.height(24.dp))

            informList.forEach {
                InformItem(
                    title = it.title,
                    description = it.description,
                    imageUrl = it.imageUrl,
                    onClick = onNextClick
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InformScreenPreview() {
    val dummyList = listOf(
        Inform(
            title = "당분간 거래중지입니다",
            description = "어떤 이의 거래 중지 의사로 인해 요청사항...",
            imageUrl = null
        ),
        Inform(
            title = "시스템 점검 안내",
            description = "6월 20일 오전 10시부터 오후 3시까지 점검 예정입니다.",
            imageUrl = null
        )
    )

    InformScreen(
        informList = dummyList,
        onBackClick = {},
        onNextClick = {},
        modifier = Modifier
    )
}
