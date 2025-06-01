package com.school_of_company.design_system.componet.icon

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.ui.graphics.Color
import com.school_of_company.design_system.componet.clickable.GwangSanClickable

@Composable
fun AddImageButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundResId: Int = com.school_of_company.design_system.R.drawable.ellipse,
    iconResId: Int = com.school_of_company.design_system.R.drawable.plus,
    iconSize: Dp = 24.dp,
    buttonSize: Dp = 50.dp,
    rippleColor: Color? = null
) {
    Box(
        modifier = modifier
            .size(buttonSize)
            .GwangSanClickable(
                rippleColor = rippleColor,
                isIndication = true,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = backgroundResId),
            contentDescription = null,
            modifier = Modifier.size(buttonSize)
        )

        Image(
            painter = painterResource(id = iconResId),
            contentDescription = "추가 아이콘",
            modifier = Modifier.size(iconSize)
        )
    }
} // 현재 이 버튼은 시각적으로 보이기만 가능합니다. 나중에 기능 추가 하겠습니다.