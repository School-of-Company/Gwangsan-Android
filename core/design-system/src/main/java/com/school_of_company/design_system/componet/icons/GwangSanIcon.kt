package com.school_of_company.design_system.componet.icons

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
    tint: Color = Color.Unspecified
) {
    Icon(
        painter = painterResource(id = R.drawable.search_icon),
        contentDescription = "검색",
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun PostWriteIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Icon(
        painter = painterResource(id = R.drawable.add_post_icon),
        contentDescription = "게시글 작성",
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun CloseIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Icon(
        painter = painterResource(id = R.drawable.close),
        contentDescription = "X",
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun EllipseIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Icon(
        painter = painterResource(id = R.drawable.ellipse),
        contentDescription = "circle",
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun PlusIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Icon(
        painter = painterResource(id = R.drawable.plus),
        contentDescription = "plus",
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun GwangSanIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Icon(
        painter = painterResource(id = R.drawable.gwangsan_icon),
        contentDescription = "광산",
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun LeftArrowIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
){
    Icon(
        painter = painterResource(id = R.drawable.chevron_down),
        contentDescription = "왼쪽 화살표",
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun DropDownIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
){
    Icon(
        painter = painterResource(id = R.drawable.chevron_down),
        contentDescription = "드롭다운",
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun CheckIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Icon(
        painter = painterResource(id = R.drawable.check),
        contentDescription = "체크",
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun BellIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
){
    Icon(
        painter = painterResource(id = R.drawable.mdi_bell_outline,),
        contentDescription = "알림",
        modifier = modifier,
        tint = tint
    )

}

@Composable
fun MainTitle(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
){
    Icon(
        painter = painterResource(id = R.drawable.name),
        contentDescription = "메인 타이틀",
        modifier = modifier,
        tint = tint
    )

}