package com.school_of_company.design_system.component.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.school_of_company.design_system.theme.GwangSanTheme

@Composable
fun GwangSanSubText(
    modifier: Modifier = Modifier,
    subjectText: String,
) {
    GwangSanTheme { colors, typography ->
        Text(
            modifier = modifier,
            text = subjectText,
            color = colors.gray500,
            style = typography.label,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun GwangSanText(
    modifier: Modifier = Modifier,
    subjectText: String,
) {
    GwangSanTheme { colors, typography ->
        Text(
            modifier = modifier,
            text = subjectText,
            color = colors.black,
            style = typography.body1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}