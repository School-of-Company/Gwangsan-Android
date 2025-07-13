package com.school_of_company.profile.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.school_of_company.design_system.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.design_system.theme.color.GwangSanColor
import com.school_of_company.model.post.response.Image
import com.school_of_company.model.post.response.Post
import com.school_of_company.model.review.response.ReviewResponseModel

@Composable
internal fun MyProfileReviewListItem(
    modifier: Modifier = Modifier,
    data : ReviewResponseModel,
    onClick: () -> Unit = {}
) {
    GwangSanTheme { colors, typography ->
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            onClick = onClick,
            modifier = modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MyProfileReviewProgressBar(
                        progress = (data.light / 100f).coerceIn(0f, 1f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                    )

                    Text(
                        text = data.content,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = data.reviewerName,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}


@Composable
private fun MyProfileReviewProgressBar(
    modifier: Modifier = Modifier,
    progress: Float,
    height: Float = 8f
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height.dp)
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val cornerRadius = canvasHeight / 2f

        drawRoundRect(
            color = GwangSanColor.gray200,
            topLeft = Offset(0f, 0f),
            size = Size(canvasWidth, canvasHeight),
            cornerRadius = CornerRadius(cornerRadius, cornerRadius)
        )

        val progressWidth = canvasWidth * progress.coerceIn(0f, 1f)
        if (progressWidth > 0f) {
            drawRoundRect(
                color = GwangSanColor.subYellow300,
                topLeft = Offset(0f, 0f),
                size = Size(progressWidth, canvasHeight),
                cornerRadius = CornerRadius(cornerRadius, cornerRadius)
            )
        }
    }
}

@Preview
@Composable
private fun MyProfileReviewListItemPreview() {
    MyProfileReviewListItem(
        data = ReviewResponseModel(
            productId = 0L,
            content = "adsfasdf",
            light = 19,
            reviewerName = "김치라"
        )
    )
}