package com.school_of_company.inform.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.R
import com.school_of_company.design_system.componet.bottombar.GwangSanNavigationBar
import com.school_of_company.design_system.componet.bottombar.GwangSanNavigationBarItem
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.CloseIcon
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.inform.component.InformItem

// 임시 모델 (API 나오면 model 패키지로 이동)
data class Inform(
    val title: String,
    val description: String,
    val imageUrl: String? = null
)

@Composable
fun InformScreen(
    informList: List<Inform>,
    onBackClick: () -> Unit = {},
    onNextClick: () -> Unit
) {
    GwangSanTheme { colors, typography ->
        Scaffold(
            containerColor = colors.white,
            topBar = {
                GwangSanSubTopBar(
                    modifier = Modifier.padding(top = 56.dp),
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
                    }
                )
            },
            bottomBar = {
                GwangSanNavigationBar {
                    val items = listOf("홈", "게시글", "채팅", "공지", "프로필")
                    val icons = listOf(
                        R.drawable.home,
                        R.drawable.copy,
                        R.drawable.chat,
                        R.drawable.horn,
                        R.drawable.person
                    )
                    items.forEachIndexed { index, item ->
                        GwangSanNavigationBarItem(
                            icon = {
                                androidx.compose.material3.Icon(
                                    painter = painterResource(id = icons[index]),
                                    contentDescription = item
                                )
                            },
                            selectedIcon = {
                                androidx.compose.material3.Icon(
                                    painter = painterResource(id = icons[index]),
                                    contentDescription = item,
                                    tint = colors.main500
                                )
                            },
                            label = {
                                Text(
                                    text = item,
                                    style = typography.label
                                )
                            },
                            selected = index == 3,
                            onClick = {}
                        )
                    }
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 20.dp)
            ) {
                Text(
                    text = "지점명 공지입니다",
                    style = typography.titleMedium2,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "본점",
                    style = typography.body5,
                    color = colors.gray400,
                    modifier = Modifier.padding(horizontal = 20.dp)
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
}

@Preview(showBackground = true)
@Composable
fun InformScreenPreview() {
    val dummyList = listOf(
        Inform(
            title = "당분간 거래중지입니다",
            description = "어떤 이의 거래 중지 의사로 인해 요청사항...",
            imageUrl = null
        ),
        Inform(
            title = "시스템 점검 안내",
            description = "6월 20일 오전 10시부터 오후 3시까지 점검 예정입니다.",
            imageUrl = "https://your.cdn.com/image1.png"
        )
    )

    InformScreen(
        informList = dummyList,
        onBackClick = {},
        onNextClick = {}
    )
}
