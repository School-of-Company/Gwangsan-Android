package com.school_of_company.design_system.componet.icons

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.ui.graphics.Color
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.EllipseIcon
import com.school_of_company.design_system.componet.icons.PlusIcon

@Composable
fun AddImageButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
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
        EllipseIcon(modifier = Modifier.size(buttonSize))

        PlusIcon(modifier = Modifier.size(iconSize))
    }
}
// 현재 이 버튼은 시각적으로 보이기만 가능합니다. 나중에 기능 추가 하겠습니다.