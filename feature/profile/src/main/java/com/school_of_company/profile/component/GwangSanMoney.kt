package com.school_of_company.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.componet.icons.GwangSanIcon
import com.school_of_company.design_system.theme.GwangSanTheme

@Composable
internal fun GwangSanMoney(
    modifier: Modifier = Modifier,
    miningAmount: Int,
) {
    GwangSanTheme { colors, typography ->

        Column(modifier = modifier.background(colors.white)) {
            Text(
                text = "광산",
                style = typography.body1,
                color = colors.black
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(colors.gray200),
            ) {
                GwangSanIcon(modifier = Modifier.padding(start = 44.dp))

                Spacer(modifier = Modifier.width(40.dp))

                Text(
                    text = "${miningAmount} 광산",
                    color = colors.subYellow700,
                    style = typography.titleMedium2,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview
@Composable
fun PreViewGwangSanMoney(){
    GwangSanMoney(
        miningAmount = 1000
    )
}