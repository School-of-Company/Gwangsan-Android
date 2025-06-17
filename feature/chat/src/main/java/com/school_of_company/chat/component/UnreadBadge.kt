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
fun UnreadBadge() {
    GwangSanTheme { colors, typography ->
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(colors.subYellow500),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "1",
                color = colors.black,
                fontSize = 10.sp
            )
        }
    }
}