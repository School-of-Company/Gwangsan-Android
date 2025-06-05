package com.school_of_company.profile.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
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
import com.school_of_company.design_system.componet.button.GwangSanEnableButton
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.DownArrowIcon
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.profile.component.BrightnessProgressBar
import com.school_of_company.profile.component.GwangSanMoney
import com.school_of_company.profile.component.MyInformation

@Composable
private fun MyProfileScreen(
    modifier: Modifier = Modifier,
    brightnessLevel: Int,
    miningAmount: Int,
) {
    GwangSanTheme { colors, typography ->

        var backgroundColor by remember { mutableStateOf(colors.white) }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
        ) {
            GwangSanSubTopBar(
                startIcon = { DownArrowIcon(modifier = Modifier.GwangSanClickable { }) },
                betweenText = "프로필",
                modifier = Modifier.padding(24.dp),
            )

            Spacer(modifier = Modifier.height(24.dp))

            MyInformation()

            HorizontalDivider(
                thickness = 12.dp,
                color = colors.gray200
            )

            BrightnessProgressBar(
                brightnessLevel = brightnessLevel,
                maxLevel = 10,
                modifier = Modifier.padding(24.dp),
            )

            Spacer(modifier = Modifier.height(32.dp))

            GwangSanMoney(
                miningAmount = miningAmount,
                modifier = Modifier.padding(24.dp),
            )

            Spacer(modifier = Modifier.height(24.dp))

            HorizontalDivider(
                thickness = 12.dp,
                color = colors.gray200
            )

            Text(
                text = "내 활동",
                style = typography.body1,
                color = colors.black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
            )

            Row(modifier = Modifier.padding(start = 24.dp, end = 24.dp)) {
                GwangSanEnableButton(
                    textStyle = typography.caption,
                    text = "내 글",
                    textColor = colors.main500,
                    backgroundColor = backgroundColor,
                    onClick = {
                        backgroundColor = colors.main500
                    },
                    modifier = Modifier
                        .weight(1f)
                        .border(
                            width = 1.dp,
                            color = colors.main500,
                            shape = RoundedCornerShape(8.dp)
                        )
                )

                GwangSanEnableButton(
                    textStyle = typography.caption,
                    text = "거래내역",
                    textColor = colors.main500,
                    backgroundColor = backgroundColor,
                    onClick = {
                        backgroundColor = colors.main500
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp, end = 8.dp)
                        .border(
                            width = 1.dp,
                            color = colors.main500,
                            shape = RoundedCornerShape(8.dp)
                        )
                )

                GwangSanEnableButton(
                    textStyle = typography.caption,
                    text = "내가 작성한 후기",
                    textColor = colors.main500,
                    backgroundColor = backgroundColor,
                    onClick = {
                        backgroundColor = colors.main500
                    },
                    modifier = Modifier
                        .weight(1f)
                        .border(
                            width = 1.dp,
                            color = colors.main500,
                            shape = RoundedCornerShape(8.dp)
                        )
                )
            }
        }
    }
}

@Preview
@Composable
private fun MyProfileScreenPreview(){
    MyProfileScreen(
        brightnessLevel = 6,
        miningAmount = 0
    )
}