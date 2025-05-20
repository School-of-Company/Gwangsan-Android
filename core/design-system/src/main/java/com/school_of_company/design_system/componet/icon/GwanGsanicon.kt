package com.school_of_company.design_system.componet.icon

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.school_of_company.design_system.R

@Composable
fun DownArrowIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Icon(
        painter = painterResource(id = R.drawable.back_icon),
        contentDescription = "뒤로가기",
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun SearchIcon(
    modifier: Modifier = Modifier,
) {
    Icon(
        painter = painterResource(id = R.drawable.search_icon),
        contentDescription = "검색",
        modifier = modifier,
    )
}

@Composable
fun PostWriteIcon(
    modifier: Modifier = Modifier,
) {
    Icon(
        painter = painterResource(id = R.drawable.add_post_icon),
        contentDescription = "게시글 작성",
        modifier = modifier,
    )
}