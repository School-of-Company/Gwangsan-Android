package com.school_of_company.design_system.theme.color

import androidx.compose.ui.graphics.Color

abstract class ColorTheme {
    // Main ColorTheme
    abstract val main500: Color
    abstract val main400: Color
    abstract val main300: Color
    abstract val main200: Color
    abstract val main100: Color
    abstract val main600: Color
    abstract val main700: Color
    abstract val main800: Color
    abstract val main900: Color

    abstract val subBlue900: Color
    abstract val subBlue800: Color
    abstract val subBlue700: Color
    abstract val subBlue600: Color
    abstract val subBlue500: Color
    abstract val subBlue400: Color
    abstract val subBlue300: Color
    abstract val subBlue200: Color
    abstract val subBlue100: Color

    abstract val subYellow900: Color
    abstract val subYellow800: Color
    abstract val subYellow700: Color
    abstract val subYellow600: Color
    abstract val subYellow500: Color
    abstract val subYellow400: Color
    abstract val subYellow300: Color
    abstract val subYellow200: Color
    abstract val subYellow100: Color
    // Gray ColorTHeme
    abstract val gray900: Color
    abstract val gray800: Color
    abstract val gray700: Color
    abstract val gray600: Color
    abstract val gray500: Color
    abstract val gray400: Color
    abstract val gray300: Color
    abstract val gray200: Color
    abstract val gray100: Color

    // System ColorTheme
    abstract val error: Color

    // Black And White ColorTheme
    abstract val black: Color
    abstract val white: Color
}