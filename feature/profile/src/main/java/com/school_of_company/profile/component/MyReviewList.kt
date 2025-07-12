package com.school_of_company.profile.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.school_of_company.design_system.R
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.model.post.response.Post

@Composable
internal fun MyReviewListItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    data: Post
) {
    GwangSanTheme { color, typography ->

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .background(color = color.white)
                .GwangSanClickable { onClick() }
                .fillMaxWidth(),
        ) {
            if (data.images.isEmpty()) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                        .size(100.dp)
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_image),
                        contentDescription = "기본 이미지",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                        .size(100.dp)
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = data.images),
                        contentDescription = "후기 이미지",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = data.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
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
