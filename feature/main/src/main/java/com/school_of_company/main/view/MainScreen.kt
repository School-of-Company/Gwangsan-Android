package com.school_of_company.main.view


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.componet.button.gwangsanfloatingbutton.GwangSanFloatingButton
import com.school_of_company.design_system.componet.clickable.GwanGsanClickable
import com.school_of_company.design_system.componet.dropdown.GwangSanSwitchButton
import com.school_of_company.design_system.componet.dropdown.state.GwangSanSwitchState
import com.school_of_company.design_system.componet.icon.DownArrowIcon
import com.school_of_company.design_system.componet.topbar.GwanGsanTopBar
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.main.componet.MainItem
import com.school_of_company.main.componet.MainList

@Composable
private fun MainScreen(
    modifier: Modifier = Modifier,
) {
    GwangSanTheme { colors, _ ->

        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 24.dp, top = 24.dp) // 버튼 영역 확보
            ) {
                GwangSanSubTopBar(
                    startIcon = { DownArrowIcon(modifier = Modifier.GwanGsanClickable { }) },
                    betweenText = "서비스"
                )

                Spacer(modifier = Modifier.height(38.dp))

                GwangSanSwitchButton(
                    stateOn = GwangSanSwitchState.NEED,
                    stateOff = GwangSanSwitchState.REQUEST,
                    initialValue = GwangSanSwitchState.NEED,
                    switchOffBackground = colors.subYellow500,
                    switchOnBackground = colors.subYellow500,
                ) {
                    when (it) {
                        GwangSanSwitchState.REQUEST -> println("현재 상태: 해주세요")
                        GwangSanSwitchState.NEED -> println("현재 상태: 필요해요")// 여기 상태에 따라 다른 data를 받고 item에 반영할 예정이에요.
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                MainList(items = dummyItems)
            }

            GwangSanFloatingButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp) // 버튼 여백
            ) {

            }
        }
    }
}

val dummyItems = listOf(
    MainItem(
        coverImage = "https://example.com/image1.jpg",
        title = "전시회 1",
        price = "무료"
    ),
    MainItem(
        coverImage = "https://example.com/image2.jpg",
        title = "전시회 2",
        price = "1000원"
    )
)

@Preview
@Composable
fun  NeighborhoodSignupScreenPreview(
){
    MainScreen()
}
