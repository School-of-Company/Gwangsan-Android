package com.school_of_company.design_system.componet.button.gwangsanfloatingbutton

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.componet.icon.PostWriteIcon
import com.school_of_company.design_system.theme.GwangSanTheme

@Composable
fun GwangSanFloatingButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    GwangSanTheme {
        colors, _ ->

        FloatingActionButton(
            modifier = modifier.size(64.dp),
            containerColor = colors.main500,
            onClick = { onClick() },
            shape = CircleShape
        ) {
            PostWriteIcon()
        }
    }
}
