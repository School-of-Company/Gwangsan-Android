package com.school_of_company.main.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.school_of_company.design_system.R
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.school_of_company.design_system.theme.GwangSanTheme

data class Notice(
    val id: Long,
    val title: String,
    val content: String,
    val image: String?,
)
@Composable
fun NoticeListItem(
    modifier: Modifier = Modifier,
    data: Notice
) {
    GwangSanTheme { color, typography ->

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .background(color = color.white)
                .fillMaxWidth(),
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp),
                        color = color.main500
                    )
                    .clip(RoundedCornerShape(10.dp))
            ) {
                if (data.image.isNullOrBlank()) {
                    Image(
                        painter = painterResource(id = R.drawable.gwangsan),
                        contentDescription = "기본 이미지",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Image(
                        painter = rememberAsyncImagePainter(model = data.image),
                        contentDescription = "네트워크 이미지",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.width(24.dp))

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = data.title,
                    style = typography.body3,
                    color = color.black
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = data.content,
                    style = typography.body5,
                    color = color.gray400
                )
            }
        }

        Spacer(modifier = Modifier.padding(bottom = 16.dp))
    }
}


@Composable
fun NoticeList(
    modifier: Modifier = Modifier,
    items: List<Notice>,
) {
    GwangSanTheme { color, _ ->

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(color.white),
        ) {
            items(items) { item ->
                NoticeListItem(
                    data = item,
                )
            }
        }
    }
}