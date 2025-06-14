package com.school_of_company.profile.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.dropdown.ServiceTypeDropdown
import com.school_of_company.design_system.componet.icons.CloseIcon
import com.school_of_company.design_system.componet.icons.DownArrowIcon
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.profile.component.MyWriteItem
import com.school_of_company.profile.component.MyWriteList
@Composable
internal fun MyWritingRoute(
    onBackClick: () -> Unit,
    onMyProfileClick: () -> Unit,
    onReviewPostDetailClick: () -> Unit,
){
    MyWritingScreen(
        onBackClick = onBackClick,
        onMyProfileClick = onMyProfileClick,
        onReviewPostDetailClick = onReviewPostDetailClick
    )
}

@Composable
private fun MyWritingScreen(
    modifier: Modifier = Modifier,
    onReviewPostDetailClick: () -> Unit,
    onBackClick: () -> Unit,
    onMyProfileClick: () -> Unit
) {
    var selectedType by remember { mutableStateOf("서비스") }
    var selectedDetail by remember { mutableStateOf("해주세요") }

    val filteredItems = dummyItems // 여기에 selectedType, selectedDetail 필터 적용 가능

    GwangSanTheme { colors, typography ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
                .padding(30.dp)
        ) {
            GwangSanSubTopBar(
                startIcon = { DownArrowIcon(modifier = Modifier.GwangSanClickable { onBackClick() }) },
                betweenText = "내 글",
                endIcon = { CloseIcon(modifier = Modifier.GwangSanClickable { onMyProfileClick() }) }
            )

            Column(modifier = Modifier.padding(vertical = 28.dp)) {
                Text(
                    text = "카테고리 선택 후 내 글 확인",
                    style = typography.titleSmall
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    ServiceTypeDropdown(
                        modifier = Modifier.weight(1f),
                        selected = selectedType,
                        items = listOf("서비스", "물건"),
                        onItemSelected = { selectedType = it }
                    )
                    ServiceTypeDropdown(
                        modifier = Modifier.weight(1f),
                        selected = selectedDetail,
                        items = listOf("해주세요", "필요해요"),
                        onItemSelected = { selectedDetail = it }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                MyWriteList(
                    onClick = { onReviewPostDetailClick() },
                    items = filteredItems
                )
            }
        }
    }
}

val dummyItems = listOf(
    MyWriteItem(
        coverImage = "https://cdn.pixabay.com/photo/1.jpg",
        title = "바퀴벌레 좀 잡아주세요",
        price = "3000 광산"
    ),
    MyWriteItem(
        coverImage = "https://cdn.pixabay.com/photo/2.jpg",
        title = "집 청소좀 해주세요",
        price = "3000 광산"
    )
)

@Composable
@Preview
private fun MyWritingScreenPreview(){
    MyWritingScreen(
        onBackClick = {},
        onMyProfileClick = {},
        onReviewPostDetailClick = {}
    )

}