package com.school_of_company.content.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.dropdown.ServiceTypeDropdown
import com.school_of_company.design_system.componet.icons.CloseIcon
import com.school_of_company.design_system.componet.recycle.MyWriteItem
import com.school_of_company.design_system.componet.recycle.MyWriteList
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.ui.previews.GwangsanPreviews

@Composable
internal fun ContentRoute(
    onMyProfileClick: () -> Unit,
    onItemClick: () -> Unit
) {

    ContentScreen(
        onMyProfileClick = onMyProfileClick,
        onItemClick = onItemClick,
        onTypeSelected = {},
        onDetailSelected = {},
        selectedType = "",
        selectedDetail = "",
    )
}

@Composable
private fun ContentScreen(
    modifier: Modifier = Modifier,
    selectedType: String,
    selectedDetail: String,
    onTypeSelected: (String) -> Unit,
    onDetailSelected: (String) -> Unit,
    onMyProfileClick: () -> Unit,
    onItemClick: () -> Unit,
) {
    val categoryMap = mapOf(
        "서비스" to listOf("해주세요", "필요해요"),
        "물건" to listOf("팔아요", "필요해요")
    )

    val detailOptions = categoryMap[selectedType] ?: emptyList()

    val filteredItems = dummyItems.filter {
        when (selectedType to selectedDetail) {
            "서비스" to "해주세요" -> it.title.contains("해주세요")
            "서비스" to "필요해요" -> it.title.contains("잡아")
            "물건" to "팔아요" -> it.title.contains("팔아요")
            "물건" to "필요해요" -> it.title.contains("필요")
            else -> true
        }
    }

    GwangSanTheme { colors, _ ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colors.white),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(52.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GwangSanSubTopBar(
                    startIcon = { Spacer(modifier = Modifier.width(24.dp)) },
                    betweenText = "게시글",
                    endIcon = {
                        CloseIcon(modifier = Modifier.GwangSanClickable { onMyProfileClick() })
                    }
                )

                Column(modifier = Modifier.padding(vertical = 28.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        ServiceTypeDropdown(
                            modifier = Modifier.weight(1f),
                            selected = selectedType,
                            items = categoryMap.keys.toList(),
                            onItemSelected = {
                                onTypeSelected(it)
                                onDetailSelected(categoryMap[it]?.first() ?: "")
                            }
                        )
                        ServiceTypeDropdown(
                            modifier = Modifier.weight(1f),
                            selected = selectedDetail,
                            items = detailOptions,
                            onItemSelected = { onDetailSelected(it) }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    MyWriteList(
                        onClick = { onItemClick() },
                        items = filteredItems
                    )
                }
            }
        }
    }
}

val dummyItems = listOf(
    MyWriteItem(
        coverImage = "https://cdn.pixabay.com/photo/1.jpg",
        title = "자전거 팔아요",
        price = "5000 광산"
    ),
    MyWriteItem(
        coverImage = "https://cdn.pixabay.com/photo/2.jpg",
        title = "바퀴벌레 좀 잡아주세요",
        price = "3000 광산"
    ),
    MyWriteItem(
        coverImage = "https://cdn.pixabay.com/photo/3.jpg",
        title = "집 청소좀 해주세요",
        price = "3000 광산"
    )
)

@GwangsanPreviews
@Composable
fun ContentScreenPreviewStates() {
    Row(modifier = Modifier.fillMaxSize()) {
        ContentScreenPreviewTemplate("선택", "선택")
        ContentScreenPreviewTemplate("서비스", "필요해요")
        ContentScreenPreviewTemplate("물건", "팔아요")
    }
}

@Composable
private fun ContentScreenPreviewTemplate(
    selectedType: String,
    selectedDetail: String
) {
    ContentScreen(
        selectedType = selectedType,
        selectedDetail = selectedDetail,
        onTypeSelected = {},
        onDetailSelected = {},
        onMyProfileClick = {},
        onItemClick = {},
        modifier = Modifier
            .width(400.dp)
            .fillMaxHeight()
            .padding(8.dp)
    )
}