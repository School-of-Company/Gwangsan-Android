package com.school_of_company.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.CircleShape
import com.school_of_company.design_system.theme.GwangSanTheme

@Composable
internal fun UnreadBadge(
    modifier: Modifier = Modifier
) {
    GwangSanTheme { colors, _ ->
        Box(
            modifier = modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(colors.subYellow500),
        )
    }
}