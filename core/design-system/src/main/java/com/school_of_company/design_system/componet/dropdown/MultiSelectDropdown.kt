package com.school_of_company.signup.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.CheckIcon
import com.school_of_company.design_system.theme.color.GwangSanColor
import com.school_of_company.design_system.theme.GwangSanTheme

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
                .background(colors.white)
        ) {
            options.forEach { option ->
                key(option) {

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
}