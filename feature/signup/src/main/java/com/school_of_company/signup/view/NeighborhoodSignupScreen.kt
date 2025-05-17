package com.school_of_company.signup.view

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.componet.button.GwangSanStateButton
import com.school_of_company.design_system.componet.button.state.ButtonState
import com.school_of_company.design_system.componet.clickable.GwanGsanClickable
import com.school_of_company.design_system.componet.icon.DownArrowIcon
import com.school_of_company.design_system.componet.topbar.GwanGsanTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.signup.componet.AreaList
import com.yourpackage.design_system.component.textField.GwangSanSearchTextField

@Composable
private fun NeighborhoodSignupScreen(
    modifier: Modifier = Modifier,
    studentSearch: String,
    neighborhoodCallBack: () -> Unit,
    focusManager: FocusManager = LocalFocusManager.current,
    scrollState: ScrollState = rememberScrollState(),
    onStudentSearchChange: (String) -> Unit,
    studentSearchCallBack: (String) -> Unit,
) {
    GwangSanTheme { colors, typography ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
                .imePadding()
                .verticalScroll(scrollState)
                .padding(
                    top = 24.dp,
                    start = 24.dp,
                    end = 24.dp
                )
                .pointerInput(Unit) {
                    detectTapGestures {
                        focusManager.clearFocus()
                    }
                }
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top =18.dp,
                            bottom = 32.dp
                        )
                    ,
                ){
                    GwanGsanTopBar(
                        startIcon = { DownArrowIcon(modifier = Modifier.GwanGsanClickable {  }) },
                        betweenText = "뒤로"
                    )
                }
                Text(
                    text = "회원가입",
                    style = typography.titleMedium2,
                    color = colors.black,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "별칭을 입력해주세요",
                    style = typography.label,
                    color = colors.black.copy(alpha = 0.5f),
                    fontWeight = FontWeight.Normal
                )

                Spacer(modifier = Modifier.height(48.dp))

                GwangSanSearchTextField(
                    placeHolder = "검색어를 입력하세요",
                    setText = studentSearch,
                    onValueChange = onStudentSearchChange,
                    onSearchTextChange = studentSearchCallBack,
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                )

                Divider(
                    color = colors.gray200,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                )

                AreaList(
                    areaList = listOf("첨단 1동", "첨단 2동")
                )
            }
            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 64.dp),
            ) {
                GwangSanStateButton(
                    text = "다음",
                    state = if (studentSearch.isNotBlank()) ButtonState.Enable else ButtonState.Disable,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    neighborhoodCallBack()
                }
            }
        }
    }
}

@Preview
@Composable
fun  NeighborhoodSignupScreenPreview(

){
    NeighborhoodSignupScreen(
        studentSearch = "",
        onStudentSearchChange = {},
        studentSearchCallBack = {},
        neighborhoodCallBack = {}
    )

}
