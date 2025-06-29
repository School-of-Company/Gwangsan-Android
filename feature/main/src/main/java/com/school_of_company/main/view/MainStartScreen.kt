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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.school_of_company.design_system.R
import com.school_of_company.design_system.componet.button.gwangsanfloatingbutton.GwangSanFloatingButton
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.BellIcon
import com.school_of_company.design_system.componet.icons.DownArrowIcon
import com.school_of_company.design_system.componet.icons.MainTitle
import com.school_of_company.design_system.componet.icons.ObjectIcon
import com.school_of_company.design_system.componet.icons.ServiceIcon
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.main.component.MainList
import com.school_of_company.main.viewmodel.uistate.GetMainListUiState
import kotlinx.coroutines.delay
import kotlin.reflect.KProperty

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
private fun AutoSlideBanner(
    modifier: Modifier = Modifier,
    imageIds: List<Int>,
    durationMillis: Long = 3000L // 3초마다 넘어감
) {
    var currentIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(durationMillis)
            currentIndex = (currentIndex + 1) % imageIds.size
        }
    }

    Image(
        painter = painterResource(id = imageIds[currentIndex]),
        contentDescription = "슬라이드 배너 이미지",
        modifier = modifier.fillMaxSize()
    )
}


@Composable
private fun MainStartScreen(
    modifier: Modifier = Modifier,
    navigationToService: () -> Unit,
    navigationToObject: () -> Unit,
) {
    GwangSanTheme { colors, typography ->

        val bannerImages = listOf(
            R.drawable.main1,
            R.drawable.main2,
            R.drawable.main3,
            R.drawable.main4,
            R.drawable.main5,
            R.drawable.main6,
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)

        ) {
            GwangSanSubTopBar(
                startIcon = { MainTitle(modifier = Modifier.GwangSanClickable { }) },
                endIcon = { BellIcon(modifier = Modifier.GwangSanClickable { }) },
                modifier = Modifier.padding(
                    top = 24.dp,
                    start = 24.dp,
                    end = 24.dp
                )
            )


            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 3.dp)
                    .height(280.dp),

            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Divider(
                        color = colors.gray400,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                    )

                    AutoSlideBanner(
                        imageIds = bannerImages,
                        modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    )

                    Divider(
                        color = colors.gray400,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 3.dp)
                            .height(1.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "광산구도시재생센터",
                style = typography.titleMedium2,
                color = colors.black
            )


            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "수완세영",
                style = typography.body4,
                color = colors.black

            )

            Spacer(modifier = Modifier.height(80.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                ServiceIcon(
                    modifier = Modifier
                        .height(180.dp)
                        .weight(1f)
                        .shadow(
                            elevation = 3.dp,
                            shape = RoundedCornerShape(10.dp), // 둥근 그림자
                            clip = false
                        )
                        .GwangSanClickable { navigationToService() }
                )

                Spacer(modifier = Modifier.width(24.dp))

                ObjectIcon(
                    modifier = Modifier
                        .height(180.dp)
                        .weight(1f)
                        .shadow(
                            elevation = 3.dp,
                            shape = RoundedCornerShape(10.dp),
                            clip = false
                        )
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

