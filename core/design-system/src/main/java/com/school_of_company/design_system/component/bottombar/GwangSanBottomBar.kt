package com.school_of_company.design_system.component.bottombar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.R
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.design_system.theme.color.GwangSanColor

@Composable
fun RowScope. GwangSanNavigationBarItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    enabled: Boolean = true,
    label: @Composable () -> Unit,
    onClick: () -> Unit,
    alwaysShowLabel: Boolean = true,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit = icon,
){
    NavigationBarItem(
        enabled = enabled,
        selected = selected,
        label = label,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = GwangSanColor.main500,
            unselectedIconColor = GwangSanColor.gray500,
            selectedTextColor = GwangSanColor.main500,
            unselectedTextColor = GwangSanColor.gray500,
            indicatorColor = GwangSanColor.white
        ) ,
        modifier = modifier
    )
}

@Composable
fun GwangSanNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    GwangSanTheme { colors, _ ->
        Column {
            HorizontalDivider(
                thickness = 1.dp,
                color = colors.gray200
            )

            NavigationBar(
                containerColor = colors.white,
                contentColor = colors.gray200,
                tonalElevation = 0.dp,
                content = content,
                modifier = modifier,
            )
        }
    }
}

@Preview
@Composable
fun ExpoNavigationPreview() {
    val items = listOf(
        "홈",
        "게시글",
        "채팅",
        "공지",
        "프로필",
    )
    val icons = listOf(
        R.drawable.home,
        R.drawable.copy,
        R.drawable.chat,
        R.drawable.horn,
        R.drawable.person,
    )
    GwangSanTheme { colors, typography ->
        GwangSanNavigationBar {
            items.forEachIndexed { index, item ->
                GwangSanNavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = icons[index]),
                            contentDescription = item
                        )
                    },
                    selectedIcon = {
                        Icon(
                            painter = painterResource(id = icons[index]),
                            contentDescription = item,
                            tint = colors.main500
                        )
                    },
                    label = {
                        Text(
                            text = item,
                            style = typography.label
                        )
                    },
                    selected = index == 0,
                    onClick = {},
                )
            }
        }
    }
}