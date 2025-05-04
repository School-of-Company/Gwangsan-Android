package com.school_of_company.design_system.theme

import androidx.compose.runtime.Composable
import com.school_of_company.design_system.theme.color.ColorTheme
import com.school_of_company.design_system.theme.color.GwangSanColor

@Composable
fun GwangSanTheme(
    colors: ColorTheme = GwangSanColor,
    typography: GwangSanTypography =GwangSanTypography,
    content: @Composable (colors: ColorTheme, typography: GwangSanTypography) -> Unit
) {
    content(colors, typography)
}