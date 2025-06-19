package com.school_of_company.inform.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.design_system.R
import com.school_of_company.design_system.componet.clickable.GwangSanClickable

@Composable
fun InformItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    imageUrl: String? = null,
    onClick: () -> Unit
) {
    GwangSanTheme { colors, typography ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .height(112.dp)
                .GwangSanClickable { onClick() }
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "공지 아이콘",
                placeholder = painterResource(id = R.drawable.gwangsan),
                error = painterResource(id = R.drawable.gwangsan),
                fallback = painterResource(id = R.drawable.gwangsan),
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 16.dp)
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = typography.body3
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = description,
                    style = typography.body5,
                    color = colors.gray400,
                    maxLines = 1
                )
            }
        }
    }
}

@Preview
@Composable
fun InformItemPreviewWithImage() {
    InformItem(
        title = "거래 중지 안내",
        description = "관리자 업로드 이미지가 있는 경우",
        imageUrl = "https://your.cdn.com/image.jpg",
        onClick = {}
    )
}

@Preview
@Composable
fun InformItemPreviewWithoutImage() {
    InformItem(
        title = "거래 중지 안내",
        description = "이미지가 없는 경우 기본 이미지 노출",
        imageUrl = null,
        onClick = {}
    )
}
