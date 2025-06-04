package com.school_of_company.profile.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.CloseIcon
import com.school_of_company.design_system.componet.icons.DownArrowIcon
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.profile.component.MyReviewItem
import com.school_of_company.profile.component.MyReviewList

@Composable
fun MyReviewScreenScreen(
    modifier: Modifier = Modifier
) {
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
                Spacer(modifier = Modifier.height(5.dp))

                MyReviewList(items = ReViewDummyItems)
            }
        }
    }
}
val ReViewDummyItems = listOf(
    MyReviewItem(
        coverImage = "https://cdn.pixabay.com/photo/1.jpg",
        title = "바퀴벌레 좀 잡아주세요",
        price = "3000 광산"
    ),
    MyReviewItem(
        coverImage = "https://cdn.pixabay.com/photo/2.jpg",
        title = "집 청소좀 해주세요",
        price = "3000 광산"
    )
)

@Preview
@Composable
fun PreViewMyReviewScreenScreen(){
    MyReviewScreenScreen()
}