package com.school_of_company.design_system.componet.topbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.componet.text.GwangSanSubText
import com.school_of_company.design_system.theme.GwangSanTheme


@Composable
fun GwangSanTopBar (
    modifier: Modifier = Modifier,
    startIcon: @Composable () -> Unit,
    betweenText: String = "",
    endIcon: @Composable () -> Unit = { Spacer(modifier = Modifier.size(24.dp)) }
) {
    GwangSanTheme { _, _ ->
        Row(
            verticalAlignment = Alignment.Top,
            modifier = modifier.fillMaxWidth()
        ) {
            startIcon()

            Spacer(modifier = Modifier.size(5.dp))

            GwangSanSubText(subjectText = betweenText)
            endIcon()
        }
    }
}
