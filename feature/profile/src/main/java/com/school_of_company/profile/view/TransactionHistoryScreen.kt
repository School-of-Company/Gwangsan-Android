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
import com.school_of_company.profile.component.DataTransactionHistoryItem
import com.school_of_company.profile.component.TransactionHistoryList


@Composable
private fun TransactionHistoryScreen(
    modifier: Modifier = Modifier
) {
    // 현재 선택된 상위 카테고리입니다. ViewModel 연동 전까지 임시로 사용합니다.
    var selectedType by remember { mutableStateOf("서비스") }

    // 현재 선택된 하위 상세 카테고리입니다. 추후 ViewModel과 연결할 예정입니다.
    var selectedDetail by remember { mutableStateOf("해주세요") }

    val filteredItems = DummyItems // 여기에 selectedType, selectedDetail 필터 적용 가능

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
                startIcon = { DownArrowIcon(modifier = Modifier.GwangSanClickable { /* 뒤로 가기 */ }) },
                betweenText = "거래내역",
                endIcon = { CloseIcon(modifier = Modifier.GwangSanClickable { /* 닫기 */ }) }
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

                TransactionHistoryList(items = filteredItems)
            }
        }
    }
}

val DummyItems = listOf(
    DataTransactionHistoryItem(
        coverImage = "https://cdn.pixabay.com/photo/1.jpg",
        title = "바퀴벌레 좀 잡아주세요",
        price = "3000 광산"
    ),
    DataTransactionHistoryItem(
        coverImage = "https://cdn.pixabay.com/photo/2.jpg",
        title = "집 청소좀 해주세요",
        price = "3000 광산"
    )
)
@Preview
@Composable
private fun PreviewTransactionHistoryScreen(
){
    TransactionHistoryScreen()
}