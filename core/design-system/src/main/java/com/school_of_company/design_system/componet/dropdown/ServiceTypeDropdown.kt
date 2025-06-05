package com.school_of_company.design_system.componet.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.DownArrowIcon
import com.school_of_company.design_system.componet.icons.DropDownIcon
import com.school_of_company.design_system.theme.GwangSanTheme

@Composable
fun ServiceTypeDropdown(
    modifier: Modifier = Modifier,
    selected: String,
    items: List<String>,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    GwangSanTheme {
            colors, typography ->

        Box(
            modifier = modifier
                .background(colors.white)
                .border(
                    width = 1.5.dp,
                    color = colors.subYellow500,
                    shape = RoundedCornerShape(16.dp)
                )
                .GwangSanClickable { expanded = true }
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selected,
                    style = typography.body4,
                    color = colors.black
                )

                DropDownIcon()
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(colors.white)
            ) {
                listOf("선택") + items // "선택" 포함
                    .forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item) },
                            onClick = {
                                onItemSelected(item)
                                expanded = false
                            }
                        )
                    }
            }
        }
    }
}

@Preview
@Composable
fun ServiceTypeDropdownPreview(){
    ServiceTypeDropdown(
        selected = "선택",
        items = listOf("서비스", "물건"),
        onItemSelected = {}

    )
}