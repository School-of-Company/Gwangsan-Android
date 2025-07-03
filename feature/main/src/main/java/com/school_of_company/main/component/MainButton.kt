package com.school_of_company.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.school_of_company.design_system.R
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.ObjectIcon
import com.school_of_company.design_system.componet.icons.ServiceIcon
import com.school_of_company.design_system.theme.GwangSanTheme

@Composable
internal fun MainButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    buttonIcon: @Composable () -> Unit,
    buttonText: String
) {

    GwangSanTheme { colors, _ ->

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .shadow(
                    elevation = 20.dp,
                    spotColor = Color(0xA0000000),
                    ambientColor = Color(0xA0000000)
                )
                .background(
                    color = colors.white,
                    shape = RoundedCornerShape(8.dp)
                )
                .wrapContentWidth()
                .height(160.dp)
                .padding(all = 24.dp)
                .GwangSanClickable { onClick() }
        ) {
                buttonIcon()

                Text(
                    text = buttonText,
                    fontFamily = FontFamily(Font(R.font.cafe_24_ssroround)),
                    fontWeight = FontWeight(700),
                    textAlign = TextAlign.Center,
                    color = colors.black,
                    fontSize = 24.sp
                )
            }
    }
}

@Preview
@Composable
private fun MainButtonPreview() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        MainButton(
            onClick = { /*TODO*/ },
            buttonIcon = { ObjectIcon() },
            buttonText = "서비스"
        )
    }
}