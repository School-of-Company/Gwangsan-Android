package com.school_of_company.inform.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.design_system.R
import com.school_of_company.design_system.component.clickable.GwangSanClickable
import com.school_of_company.model.notice.response.GetAllNoticeResponseModel
import com.school_of_company.model.notice.response.ImageModel

@Composable
internal fun InformItem(
    modifier: Modifier = Modifier,
    item: GetAllNoticeResponseModel,
    onClick: (Long) -> Unit
) {
    GwangSanTheme { colors, typography ->

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .GwangSanClickable { onClick(item.id) }
                .padding(bottom = 16.dp)
        ) {
            AsyncImage(
                model = item.images,
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
                    text = item.title,
                    style = typography.body3,
                    color = colors.black
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = item.content,
                    style = typography.body5,
                    color = colors.gray400,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
internal fun InformList(
    modifier: Modifier = Modifier,
    items: List<GetAllNoticeResponseModel>,
    onClick: (Long) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        items(
            items = items,
            key = { it.id }
        ) { item ->
            InformItem(
                onClick = onClick,
                item = item
            )
        }
    }
}

@Preview
@Composable
private fun InformItemPreviewWithImage() {
    InformItem(
        item = GetAllNoticeResponseModel(
            id = 1,
            title = "거래 중지 안내",
            content = "거래 중지 안내 내용입니다.",
            images = listOf(ImageModel(
                imageId = 1,
                imageUrl = "https://example.com/image.jpg"
            ))
        ),
        onClick = {}
    )
}