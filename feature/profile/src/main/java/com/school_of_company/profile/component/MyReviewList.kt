package com.school_of_company.profile.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.school_of_company.design_system.R
import com.school_of_company.design_system.theme.GwangSanTheme

@Composable
internal fun MyReviewListItem(
    modifier: Modifier = Modifier,
    coverImage: String?,
    title: String,
    price: String
) {
    GwangSanTheme { color, typography ->

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .background(color = color.white)
                .fillMaxWidth(),
        ) {
            if (coverImage.isNullOrEmpty()) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_image),
                        contentDescription = "기본 이미지",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            else {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = coverImage),
                        contentDescription = "후기 이미지",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.width(24.dp))

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = typography.body3,
                    color = color.black
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = price,
                    style = typography.body5,
                    color = color.black
                )
            }
        }
    }
}

@Composable
internal fun MyReviewList(
    modifier: Modifier = Modifier,
    items: List<MyReviewItem>
) {
    GwangSanTheme { color, _ ->

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(color.white),
        ) {
            items(items) { item ->
                MyReviewListItem(
                    coverImage = item.coverImage,
                    title = item.title,
                    price = item.price
                )
            }
        }
    }
}

@Preview
@Composable
private fun MyReviewListItemPreview() {
    MyReviewListItem(
        coverImage = null,
        title = "바퀴벌레 후기",
        price = "1000원"
    )
}

data class MyReviewItem(
    val coverImage: String,
    val title: String,
    val price: String
)
