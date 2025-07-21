package com.school_of_company.signup.view

import android.content.ContextWrapper
import androidx.activity.ComponentActivity
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.school_of_company.design_system.component.button.GwangSanStateButton
import com.school_of_company.design_system.component.button.state.ButtonState
import com.school_of_company.design_system.component.clickable.GwangSanClickable
import com.school_of_company.design_system.component.icons.DownArrowIcon
import com.school_of_company.design_system.component.topbar.GwangSanTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.signup.componet.AreaList
import com.school_of_company.signup.viewmodel.SignUpViewModel
import com.school_of_company.ui.previews.GwangsanPreviews
import com.yourpackage.design_system.component.textField.GwangSanSearchTextField
@Composable
internal fun NeighborhoodSignupRoute(
    onBackClick: () -> Unit,
    onIntroduceClick: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel(
        viewModelStoreOwner = LocalContext.current.let { context ->
            var ctx = context
            while (ctx is ContextWrapper) {
                if (ctx is ComponentActivity) return@let ctx
                ctx = ctx.baseContext
            }
            ctx as ComponentActivity
        }
    )) {
    val studentSearch by viewModel.dong.collectAsStateWithLifecycle()
    val filteredAreas by viewModel.filteredAreas.collectAsStateWithLifecycle()

    NeighborhoodSignupScreen(
        studentSearch = studentSearch,
        filteredAreas = filteredAreas,
        onStudentSearchChange = viewModel::onDongChange,
        onAreaClick = viewModel::onAreaSelected,
        onBackClick = onBackClick,
        onIntroduceClick = onIntroduceClick
    )
}

@Composable
private fun NeighborhoodSignupScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onIntroduceClick: () -> Unit,
    studentSearch: String,
    filteredAreas: List<String>,
    focusManager: FocusManager = LocalFocusManager.current,
    scrollState: ScrollState = rememberScrollState(),
    onStudentSearchChange: (String) -> Unit,
    onAreaClick: (String) -> Unit,
) {
    GwangSanTheme { colors, typography ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
                .verticalScroll(scrollState)
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                .pointerInput(Unit) {
                    detectTapGestures { focusManager.clearFocus() }
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
                            top = 56.dp,
                            bottom = 32.dp
                        ),
                ) {
                    GwangSanTopBar(
                        startIcon = { DownArrowIcon(modifier = Modifier.GwangSanClickable { onBackClick() }) },
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
                    text = "동네를 선택해주세요",
                    style = typography.label,
                    color = colors.black.copy(alpha = 0.5f),
                    fontWeight = FontWeight.Normal
                )

                Spacer(modifier = Modifier.height(48.dp))

                GwangSanSearchTextField(
                    placeHolder = "동네를 검색해주세요",
                    setText = studentSearch,
                    onValueChange = onStudentSearchChange,
                    onSearchTextChange = onStudentSearchChange,
                    modifier = Modifier.fillMaxWidth()
                )

                Divider(
                    color = colors.gray200,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                )

                AreaList(
                    filteredAreas = filteredAreas,
                    onItemClick = onAreaClick
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
                    state = if (allAreas.contains(studentSearch)) ButtonState.Enable else ButtonState.Disable,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    onIntroduceClick()
                }
            }
        }
    }
}

private val allAreas = listOf(
    "동곡동",
    "도산동",
    "평동",
    "운남동",
    "첨단1동",
    "첨단2동",
    "송정1동",
    "송정2동",
    "우산동",
    "신가동",
    "신흥동",
    "수완동",
    "임곡동",
    "본량동",
    "월곡1동",
    "월곡2동",
    "하남동",
    "비아동",
    "어룡동",
    "삼도동"
)

@GwangsanPreviews
@Composable
private fun NeighborhoodSignupScreenPreview() {
    NeighborhoodSignupScreen(
        studentSearch = "",
        filteredAreas = listOf("강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구",),
        onStudentSearchChange = {},
        onAreaClick = {},
        onBackClick = {},
        onIntroduceClick = {}
    )
}