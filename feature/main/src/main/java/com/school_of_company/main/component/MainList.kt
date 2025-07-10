package com.school_of_company.main.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.school_of_company.model.post.response.Post

@Composable
fun MainListItem(
    modifier: Modifier = Modifier,
    data: Post
) {
    GwangSanTheme { color, typography ->

        Row(
            modifier = modifier
                .background(color = color.white)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (data.imageUrls.images.isEmpty()) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_image),
                        contentDescription = "바퀴벌레 이미지",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.width(24.dp))

                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxWidth(),

                ) {
                    Text(
                        text = data.title,
                        style = typography.body3,
                        color = color.black
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "${data.gwangsan}",
                        style = typography.body5,
                        color = color.black
                    )

                }
            }
            else {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = data.imageUrls),
                        contentDescription = "바퀴벌레 이미지",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
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
                        text = "${data.gwangsan}",
                        style = typography.body5,
                        color = color.black
                    )

                }

            }
        }
    }
}

@Composable
fun MainList(
    modifier: Modifier = Modifier,
    items: List<Post>
) {
    GwangSanTheme { color, _ ->

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(color.white),
        ) {
            items(items) { item ->
                MainListItem(
                    data = item
                )
            }
        }
    }
}