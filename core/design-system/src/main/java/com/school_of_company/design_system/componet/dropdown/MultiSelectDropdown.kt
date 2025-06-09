package com.school_of_company.signup.view

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.componet.button.GwangSanStateButton
import com.school_of_company.design_system.componet.button.state.ButtonState
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.CheckIcon
import com.school_of_company.design_system.componet.icons.DownArrowIcon
import com.school_of_company.design_system.componet.icons.PlusIcon
import com.school_of_company.design_system.componet.topbar.GwangSanTopBar
import com.school_of_company.design_system.theme.color.GwangSanColor
import com.yourpackage.design_system.component.textField.GwangSanTextField
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.design_system.theme.GwangSanTypography.body5

@Composable
fun MultiSelectDropdown(
    modifier: Modifier = Modifier,
    options: List<String>,
    selectedOptions: List<String>,
    onSelectionChange: (List<String>) -> Unit,
    onDismissRequest: () -> Unit
) {
    GwangSanTheme { colors, typography ->
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(12.dp))
                .background(GwangSanColor.white)
        ) {
            options.forEach { option ->
                val isSelected = selectedOptions.contains(option)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = if (isSelected) colors.focus else colors.white,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .GwangSanClickable {
                            val newSelection = if (isSelected) {
                                selectedOptions - option
                            } else {
                                selectedOptions + option
                            }
                            onSelectionChange(newSelection)
                        }
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    if (isSelected) {
                        CheckIcon(modifier = Modifier.padding(end = 8.dp))
                    } else {
                        Spacer(modifier = Modifier.width(24.dp))
                    }
                    Text(
                        text = option,
                        style = typography.body5,
                        color = if (isSelected) colors.black else colors.gray600
                    )
                }
            }
        }
    }
}