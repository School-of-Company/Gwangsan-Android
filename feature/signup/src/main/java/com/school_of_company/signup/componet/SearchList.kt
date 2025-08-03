package com.school_of_company.signup.componet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.clickable.GwangSanClickable
import com.school_of_company.design_system.theme.GwangSanTheme
import kotlinx.collections.immutable.PersistentList

@Composable
fun AreaListItem(
    modifier: Modifier = Modifier,
    areaName: String,
    onClick: () -> Unit = {}
) {
    GwangSanTheme { colors, typography ->
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = modifier
                .fillMaxWidth()
                .GwangSanClickable { onClick() }
                .padding(top = 24.dp, bottom = 24.dp),
        ) {
            Text(
                text = areaName,
                color = colors.black,
                style = typography.body3
            )
            Spacer(modifier = Modifier.height(24.dp))
            Divider(color = colors.gray200)
        }
    }
}

@Composable
fun AreaList(
    modifier: Modifier = Modifier,
    filteredAreas: PersistentList<String>,
    onItemClick: (String) -> Unit
) {
    GwangSanTheme { colors, _ ->
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .heightIn(max = 10000.dp)
        ) {
            items(
                items = filteredAreas,
                key = { areaName -> areaName }
            ) { areaName ->
                Column(modifier = Modifier.fillMaxWidth()) {
                    AreaListItem(
                        onClick = { onItemClick(areaName) },
                        areaName = areaName
                    )
                    Divider(
                        color = colors.white,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}