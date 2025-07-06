package com.school_of_company.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.ArrowDown
import com.school_of_company.design_system.componet.icons.EllipseIcon
import com.school_of_company.design_system.theme.GwangSanTheme

@Composable
internal fun MyInformation(
    modifier: Modifier = Modifier,
    onModifyClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    GwangSanTheme { colors, typography ->

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxWidth()
                .background(colors.white)
                .padding(16.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                EllipseIcon(modifier = Modifier.size(50.dp))

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text(
                        text = "모태환",
                        style = typography.body1,
                        color = colors.black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically){
                        Text(
                            text = "로그아웃하기",
                            style = typography.label,
                            color = colors.gray500
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        ArrowDown(modifier = Modifier.GwangSanClickable { onLogoutClick() })
                    }
                }
            }

            MyProfileModifyButton(
                onClick = onModifyClick
            )
        }
    }
}

@Preview
@Composable
private fun PreviewMyInformation(

){
    MyInformation(
        onModifyClick = {},
        onLogoutClick = {}
    )
}