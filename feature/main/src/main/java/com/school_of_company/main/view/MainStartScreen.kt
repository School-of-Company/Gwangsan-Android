package com.school_of_company.main.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.R
import com.school_of_company.design_system.componet.button.gwangsanfloatingbutton.GwangSanFloatingButton
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.dropdown.GwangSanSwitchButton
import com.school_of_company.design_system.componet.dropdown.state.GwangSanSwitchState
import com.school_of_company.design_system.componet.icons.BellIcon
import com.school_of_company.design_system.componet.icons.DownArrowIcon
import com.school_of_company.design_system.componet.icons.MainTitle
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.main.component.MainList
import com.school_of_company.main.viewmodel.uistate.GetMainListUiState

@Composable
internal fun MainStartRoute(
    navigationToService: () -> Unit,
    navigationToObject: () -> Unit,
) {
    MainStartScreen(
        navigationToService = navigationToService,
        navigationToObject = navigationToObject,
    )
}

@Composable
private fun MainStartScreen(
    modifier: Modifier = Modifier,
    navigationToService: () -> Unit,
    navigationToObject: () -> Unit,
) {
    GwangSanTheme { colors, _ ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
                .padding(
                    start = 24.dp,
                    top = 24.dp,
                    end = 24.dp
                )
        ) {
            GwangSanSubTopBar(
                startIcon = { MainTitle(modifier = Modifier.GwangSanClickable { }) },
                endIcon = { BellIcon(modifier = Modifier.GwangSanClickable { }) }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Image(
                        painter = painterResource(id = R.drawable.main),
                        contentDescription = "메인 이미지",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.service),
                    contentDescription = "서비스",
                    modifier = Modifier
                        .weight(1f)
                        .GwangSanClickable { navigationToService() }
                )

                Image(
                    painter = painterResource(id = R.drawable.service),
                    contentDescription = "물건",
                    modifier = Modifier
                        .weight(1f)
                        .GwangSanClickable { navigationToObject() }
                )
            }
        }
    }
}
@Composable
@Preview
fun MainStartScreenPreview() {
    MainStartScreen(
        navigationToService = {},
        navigationToObject = {},
    )
}